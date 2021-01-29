package com.gl.device.service;

import com.gl.device.model.Device;

import java.util.List;

public interface DeviceProviderService {

    void registerDevice(final Device device);

    List<Device> retrieveAllDevices();

    Device retrieveDevice(final String macAddress);

    List<Device> retrieveAllDevicesTopology();

    Device retrieveDeviceTopology(final String macAddress);

}
