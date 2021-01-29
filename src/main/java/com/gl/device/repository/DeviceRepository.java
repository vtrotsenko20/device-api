package com.gl.device.repository;

import com.gl.device.model.Device;

import java.util.List;

public interface DeviceRepository {

    void add(Device device);

    Device retrieveByMacAddress(String macAddress);

    List<Device> retrieveAllRegisteredDevices();

    List<Device> retrieveAllDevicesTopology();

    Device retrieveDeviceTopology(String macAddress);
}
