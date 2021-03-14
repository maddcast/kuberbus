package com.antilogics.servicebus.core;

import java.io.IOException;

public interface HttpResponder {
    void respond(HttpMessage httpMessage) throws IOException;
}
