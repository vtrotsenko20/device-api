package com.gl.device.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.gl.device.model.Device;
import com.gl.device.model.DeviceView;
import com.gl.device.service.DeviceProviderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceProviderController {

    @Autowired
    private DeviceProviderServiceImpl deviceProviderService;

    @JsonView(DeviceView.Basic.class)
    @PostMapping("register")
    public String register(@RequestBody Device device) {
        deviceProviderService.registerDevice(device);
        return "Device was successfully registered!";
    }

    @JsonView(DeviceView.Basic.class)
    @GetMapping("{macAddress}")
    public Device getDevice(@PathVariable("macAddress") String macAddress) {
        return deviceProviderService.retrieveDevice(macAddress);
    }

    @JsonView(DeviceView.Basic.class)
    @GetMapping("all")
    public List<Device> getAllDevices() {
        return deviceProviderService.retrieveAllDevices();
    }

    @JsonView(DeviceView.Topology.class)
    @GetMapping("topology")
    public List<Device> getAllDevicesTopology() {
        return deviceProviderService.retrieveAllDevicesTopology();
    }

    @JsonView(DeviceView.Topology.class)
    @GetMapping("topology/{macAddress}")
    public Device getDeviceTopology(@PathVariable("macAddress") String macAddress) {
        return deviceProviderService.retrieveDeviceTopology(macAddress);
    }
}
