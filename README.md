# device-api

In order to run the application, please clone the project to your local environment and make sure you are running Redis instance and specify connection properties in application.yaml.
To run the application:
1. Navigate to the root folder of the project;
2. Execute comman from the terminal:
`mvn spring-boot:run`

List of available endpoints:
* POST /devices/register - Registering a device to a network deployment
Request body should be:
```
{
    "macAddress":"",
    "deviceType":"",
    "uplinkMacAddress":""
}
```

* GET /devices/all - Retrieving all registered devices, sorted by device type
* GET /devices/{macAddress} - Retrieving network deployment device by MAC address
* GET /devices/topology - Retrieving all registered network device topology
* GET /devices/topology/{macAddress} - Retrieving network device topology starting from a specific device
