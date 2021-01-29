package com.gl.device.service;

import com.gl.device.model.DeviceType;
import com.gl.device.repository.DeviceRepositoryImpl;
import com.gl.device.util.TestDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceProviderServiceTest {

    @Mock
    private DeviceRepositoryImpl deviceRepository;

    @InjectMocks
    private DeviceProviderServiceImpl deviceProviderService;

    @Test
    public void testRegisterDevice() {
        var device = TestDataProvider.createTestHeadDevice();
        deviceProviderService.registerDevice(device);
    }

    @Test
    public void testRetrieveAllRegisteredDevices() {
        var devices = TestDataProvider.createAllTestDevices();

        when(deviceProviderService.retrieveAllDevices()).thenReturn(devices);

        var allDevices = deviceProviderService.retrieveAllDevices();
        assertThat(allDevices).isNotNull();
        assertThat(allDevices).isNotEmpty();
        assertThat(allDevices.size()).isEqualTo(3);
    }

    @Test
    public void testRetrieveRegisteredDevice() {
        var macAddress = TestDataProvider.TEST_MAC_ADDRESS_1;
        var registeredDevice = TestDataProvider.createTestSubDevice();

        when(deviceProviderService.retrieveDevice(any())).thenReturn(registeredDevice);

        var device = deviceProviderService.retrieveDevice(macAddress);
        assertThat(device).isNotNull();
        assertThat(device.getDeviceType()).isEqualTo(DeviceType.SWITCH);
        assertThat(device.getUplinkMacAddress()).isEqualTo(TestDataProvider.TEST_UPLINK_MAC_ADDRESS);
    }

    @Test
    public void testRetrieveUnregisteredDevice() {
        var unregisteredMacAddress = TestDataProvider.TEST_UNREGISTERED_MAC_ADDRESS;

        when(deviceProviderService.retrieveDevice(unregisteredMacAddress)).thenReturn(null);

        var device = deviceProviderService.retrieveDevice(unregisteredMacAddress);
        assertThat(device).isNull();
    }

    @Test
    public void testRetrieveAllRegisteredDevicesTopology() {
        var devicesTopology = TestDataProvider.createAllDevicesTopology();

        when(deviceRepository.retrieveAllDevicesTopology()).thenReturn(devicesTopology);

        var topology = deviceProviderService.retrieveAllDevicesTopology();
        assertThat(topology).isNotNull();
        assertThat(topology).isNotEmpty();
        assertThat(topology.size()).isEqualTo(1);

        var headOfTopology = topology.get(0);
        assertThat(headOfTopology.getChildren().size()).isEqualTo(2);
        assertThat(headOfTopology.getChildren().get(0).getUplinkMacAddress()).isEqualTo(headOfTopology.getMacAddress());
        assertThat(headOfTopology.getChildren().get(1).getUplinkMacAddress()).isEqualTo(headOfTopology.getMacAddress());
    }
}
