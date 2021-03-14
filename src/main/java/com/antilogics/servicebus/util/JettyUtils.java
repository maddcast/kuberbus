package com.antilogics.servicebus.util;

import com.antilogics.servicebus.core.HttpMessage;
import org.eclipse.jetty.server.Request;

import java.io.IOException;
import java.util.HashMap;

public class JettyUtils {
    public static HttpMessage createMessage(Request httpServletRequest) throws IOException {
        var headers = new HashMap<String, String>();
        var headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, httpServletRequest.getHeader(headerName));
        }

        return new HttpMessage(0,
                httpServletRequest.getHttpURI().getPathQuery(),
                httpServletRequest.getMethod(),
                headers,
                httpServletRequest.getInputStream().readAllBytes());
    }
}
