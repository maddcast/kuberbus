package com.antilogics.servicebus.core.impl;

import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.HttpResponder;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JettyHttpResponder implements HttpResponder {
    private final HttpServletResponse httpServletResponse;


    public JettyHttpResponder(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }


    @Override
    public void respond(HttpMessage httpMessage) throws IOException {
        if (httpMessage.getStatus() != 0) {
            httpServletResponse.setStatus(httpMessage.getStatus());
        }
        if (!httpMessage.getHeaders().isEmpty()) {
            httpMessage.getHeaders().forEach(httpServletResponse::setHeader);
        }
        if (httpMessage.getBodyAsBytes().length > 0) {
            httpServletResponse.getOutputStream().write(httpMessage.getBodyAsBytes());
        }
    }
}
