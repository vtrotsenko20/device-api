package com.gl.device.repository;

import com.gl.device.model.Device;
import com.gl.device.model.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public static final String EMPTY_PARAM_VALUE = "-1";

    @Autowired
    public DeviceRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void add(Device device) {
        var value = (device.getDeviceType() != null ? device.getDeviceType().toString() : EMPTY_PARAM_VALUE) + ";"
                + (!device.getUplinkMacAddress().isEmpty() ? device.getUplinkMacAddress() : EMPTY_PARAM_VALUE);
        redisTemplate.opsForValue().set(device.getMacAddress(), value);
    }

    public Device retrieveByMacAddress(String macAddress) {
        var value = redisTemplate.opsForValue().get(macAddress);
        if (value == null)
            return null;

        return parseResultValue(macAddress, value);
    }

    public List<Device> retrieveAllRegisteredDevices() {
        var allKeys = redisTemplate.keys("*");
        if (allKeys == null)
            return null;

        return allKeys.stream()
                .map(this::retrieveByMacAddress)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Device::getDeviceType))
                .collect(Collectors.toList());
    }

    public List<Device> retrieveAllDevicesTopology() {
        var allDevices = retrieveAllRegisteredDevices();
        var initialSize = allDevices.size();
        var counter = 1;

        while (counter != initialSize) {
            var device = allDevices.get(allDevices.size() - 1);
            var uplinkDevice = findDevice(device.getUplinkMacAddress(), allDevices);
            if (uplinkDevice != null) {
                uplinkDevice.addChild(device);
                allDevices.remove(device);
            }
            counter++;
        }
        return allDevices;
    }

    public Device retrieveDeviceTopology(String macAddress) {
        var allDevicesTopology = retrieveAllDevicesTopology();
        return findDevice(macAddress, allDevicesTopology);
    }


    private Device findDevice(String uplinkMacAddress, List<Device> devices) {
        if (uplinkMacAddress == null || devices == null || devices.size() == 0)
            return null;

        var retDevice = devices.stream()
                .filter(d -> d.getMacAddress().equals(uplinkMacAddress))
                .findFirst()
                .orElse(null);

        if (retDevice == null) {
            for (var d : devices) {
                retDevice = findDevice(uplinkMacAddress, d.getChildren());
                if (retDevice != null)
                    break;
            }
        }
        return retDevice;
    }

    private Device parseResultValue(String key, String value) {
        String[] params = value.split(";");
        var deviceType = params[0] != null ? DeviceType.valueOf(params[0]) : null;
        var uplinkMacAddress = !params[1].equals(EMPTY_PARAM_VALUE) ? params[1] : null;
        return new Device(key, deviceType, uplinkMacAddress);
    }
}
