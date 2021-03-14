package com.antilogics.servicebus.core;

import lombok.Value;
import lombok.With;

import java.util.Map;

@Value
@With
public class HttpMessage {
    int status;
    String path;
    String method;
    Map<String, String> headers;
    byte[] bodyAsBytes;
}
