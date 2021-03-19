package com.antilogics.servicebus.core;

import lombok.Value;

import java.io.IOException;

public interface ServerContext {
    void sleep(long millis);
    void respond(HttpMessage httpMessage) throws IOException;
}
