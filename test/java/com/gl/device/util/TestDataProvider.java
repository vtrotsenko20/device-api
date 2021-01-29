package com.gl.device.util;

import com.gl.device.model.Device;
import com.gl.device.model.DeviceType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestDataProvider {

    public static final String TEST_MAC_ADDRESS_1 = "01000ABB28FC";
    public static final String TEST_MAC_ADDRESS_2 = "02000FBB658FC";
    public static final String TEST_UPLINK_MAC_ADDRESS = "10000XBB58FC";
    public static final String TEST_UNREGISTERED_MAC_ADDRESS = "09000XBB56AFC";
    public static final DeviceType TEST_DEVICE_TYPE_GATEWAY = DeviceType.GATEWAY;
    public static final DeviceType TEST_DEVICE_TYPE_SWITCH = DeviceType.SWITCH;

    public static Device createTestHeadDevice() {
        return new Device(TEST_UPLINK_MAC_ADDRESS, TEST_DEVICE_TYPE_GATEWAY, null);
    }

    public static Device createTestSubDevice() {
        return new Device(TEST_MAC_ADDRESS_1, TEST_DEVICE_TYPE_SWITCH, TEST_UPLINK_MAC_ADDRESS);
    }

    public static List<Device> createAllTestDevices() {
        return Arrays.asList(
                createTestHeadDevice(),
                createTestSubDevice(),
                new Device(TEST_MAC_ADDRESS_2, DeviceType.ACCESS_POINT, TEST_UPLINK_MAC_ADDRESS)
        );
    }

    public static List<Device> createAllDevicesTopology() {
        var subDevice1 = createTestSubDevice();
        var subDevice2 = new Device(TEST_MAC_ADDRESS_2, DeviceType.ACCESS_POINT, TEST_UPLINK_MAC_ADDRESS);
        var headDevice = createTestHeadDevice();
        headDevice.addChild(subDevice1);
        headDevice.addChild(subDevice2);

        return Collections.singletonList(headDevice);
    }
}
