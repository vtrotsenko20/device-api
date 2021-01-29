package com.gl.device.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties(prefix = "datasource.redis")
public class DataSourceProperties {

    @NotEmpty
    private String host;

    private int port;

}
