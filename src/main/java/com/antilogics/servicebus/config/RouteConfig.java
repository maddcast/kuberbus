package com.antilogics.servicebus.config;

import com.antilogics.servicebus.config.steps.AbstractStepConfig;
import lombok.Data;

import java.util.List;

@Data
public class RouteConfig {
    private String path;
    private String method;
    private List<AbstractStepConfig> steps;
}
