package com.gl.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device implements Serializable {

    @JsonView({DeviceView.Topology.class, DeviceView.Basic.class})
    protected String macAddress;

    @JsonView({DeviceView.Topology.class, DeviceView.Basic.class})
    protected DeviceType deviceType;

    @JsonView(DeviceView.Basic.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String uplinkMacAddress;

    @JsonView(DeviceView.Topology.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Device> children = new LinkedList<>();

    public Device(String macAddress, DeviceType deviceType, String uplinkMacAddress) {
        this.macAddress = macAddress;
        this.deviceType = deviceType;
        this.uplinkMacAddress = uplinkMacAddress;
    }

    public void addChild(Device child) {
        children.add(child);
    }
}
