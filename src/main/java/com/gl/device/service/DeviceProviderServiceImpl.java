package com.gl.device.service;

import com.gl.device.model.Device;
import com.gl.device.repository.DeviceRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceProviderServiceImpl implements DeviceProviderService {

    @Autowired
    private DeviceRepositoryImpl deviceRepository;


    public void registerDevice(Device device) {
        deviceRepository.add(device);
    }

    public List<Device> retrieveAllDevices() {
        return deviceRepository.retrieveAllRegisteredDevices();
    }

    public Device retrieveDevice(String macAddress) {
        return deviceRepository.retrieveByMacAddress(macAddress);
    }

    public List<Device> retrieveAllDevicesTopology() {
        return deviceRepository.retrieveAllDevicesTopology();
    }

    public Device retrieveDeviceTopology(String macAddress) {
        return deviceRepository.retrieveDeviceTopology(macAddress);
    }
}
