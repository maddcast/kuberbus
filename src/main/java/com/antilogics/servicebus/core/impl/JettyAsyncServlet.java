package com.antilogics.servicebus.core.impl;

import com.antilogics.servicebus.config.ApiConfig;
import com.antilogics.servicebus.config.RouteConfig;
import com.antilogics.servicebus.config.steps.AbstractStepConfig;
import com.antilogics.servicebus.util.JettyUtils;
import io.sentry.Sentry;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class JettyAsyncServlet extends HttpServlet {
    private final ApiConfig apiConfig;
    private final AtomicInteger pipeId = new AtomicInteger(0);


    public JettyAsyncServlet(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        log.info("Initialize routes");
        for (RouteConfig routeConfig : apiConfig.getRoutes()) {
            for (AbstractStepConfig stepConfig : routeConfig.getSteps()) {
                stepConfig.afterPropertiesSet();
            }
        }
    }


    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final AsyncContext asyncContext = request.startAsync();
        asyncContext.start(new Runnable()
        {
            @Override
            @SneakyThrows
            public void run()
            {
                for (RouteConfig routeConfig : apiConfig.getRoutes()) {
                    if (routeFits(request, routeConfig)) {
                        var httpMessage = JettyUtils.createMessage((Request)request);
                        var serverContext = new JettyServerContext(response, asyncContext);
                        int currentPipeId = pipeId.incrementAndGet();
                        try {
                            for (AbstractStepConfig stepConfig : routeConfig.getSteps()) {
                                var command = stepConfig.toCommand();
                                var commandResult = command.process(currentPipeId, httpMessage, serverContext);
                                if (commandResult.isContinuePipeline()) {
                                    if (commandResult.getResultMessage() != null) {
                                        httpMessage = commandResult.getResultMessage();
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            Sentry.captureException(e);
                            throw e;
                        }
                        return;
                    }
                }
            }
        });
    }


    private boolean routeFits(HttpServletRequest request, RouteConfig routeConfig) {
        boolean pathFits = request.getPathInfo().startsWith(routeConfig.getPath());
        boolean methodFits = routeConfig.getMethod().equals("*") || routeConfig.getMethod().equalsIgnoreCase(request.getMethod());
        return pathFits && methodFits;
    }
}
