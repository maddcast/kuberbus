package com.antilogics.servicebus.core.impl;

import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.ServerContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

public class JettyServerContext implements ServerContext {
    private final HttpServletResponse httpServletResponse;


    public JettyServerContext(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }


    @Override
    @SneakyThrows
    public void sleep(long millis) {
        Thread.sleep(millis);
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
