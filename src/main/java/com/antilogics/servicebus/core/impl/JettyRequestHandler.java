package com.antilogics.servicebus.core.impl;

import com.antilogics.servicebus.config.ApiConfig;
import com.antilogics.servicebus.config.RouteConfig;
import com.antilogics.servicebus.config.steps.AbstractStepConfig;
import com.antilogics.servicebus.util.JettyUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class JettyRequestHandler extends AbstractHandler {
    private final ApiConfig apiConfig;
    private final AtomicInteger pipeId = new AtomicInteger(0);


    public JettyRequestHandler(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }


    @Override
    public void handle(String path, Request jettyRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        for (RouteConfig routeConfig : apiConfig.getRoutes()) {
            if (routeFits(httpServletRequest, routeConfig)) {
                var httpMessage = JettyUtils.createMessage(jettyRequest);
                var responder = new JettyHttpResponder(httpServletResponse);
                int currentPipeId = pipeId.incrementAndGet();
                for (AbstractStepConfig stepConfig : routeConfig.getSteps()) {
                    var command = stepConfig.toCommand();
                    var commandResult = command.process(currentPipeId, httpMessage, responder);
                    if (commandResult.isContinuePipeline()) {
                        if (commandResult.getResultMessage() != null) {
                            httpMessage = commandResult.getResultMessage();
                        }
                    }
                    else {
                        break;
                    }
                }
                jettyRequest.setHandled(true);
                return;
            }
        }
    }


    private boolean routeFits(HttpServletRequest request, RouteConfig routeConfig) {
        boolean pathFits = request.getPathInfo().startsWith(routeConfig.getPath());
        boolean methodFits = routeConfig.getMethod().equals("*") || routeConfig.getMethod().equalsIgnoreCase(request.getMethod());
        return pathFits && methodFits;
    }
}
