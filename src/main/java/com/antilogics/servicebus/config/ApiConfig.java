package com.antilogics.servicebus.config;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ApiConfig {
    private int port;
    private List<RouteConfig> routes;
}
