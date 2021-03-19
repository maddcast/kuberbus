package com.antilogics.servicebus.core;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class RequestCacheKey {
    String path;
    byte[] requestBody;
}
