package com.antilogics.servicebus;

import com.antilogics.servicebus.config.ApiConfig;
import com.antilogics.servicebus.core.impl.JettyRequestHandler;
import com.antilogics.servicebus.core.impl.JettyServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ServiceBus {
    public static void main(String[] args) throws Exception {
        setupSentry();

        var apiConfig = loadConf(args);

        if (apiConfig == null) {
            System.out.println("Pass config file path as command line argument or inside environment variable API_CONF.");
            System.exit(1);
        }

        log.info("Parsed config {}", apiConfig);

        var server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(apiConfig.getPort());
        server.addConnector(connector);

//        var servletHandler = new ServletContextHandler();
//        servletHandler.addServlet(new JettyServlet())
        server.setHandler(new JettyRequestHandler(apiConfig));
        server.start();
    }


    private static void setupSentry() {
        boolean sentryEnabled = Boolean.parseBoolean(System.getenv().getOrDefault("SENTRY_ENABLED", "false"));
        if (sentryEnabled) {
            String sentryDsn = System.getenv("SENTRY_DSN");
            Sentry.init(options -> options.setDsn(sentryDsn));
            log.info("Enabled sentry logging");
        }
    }


    private static ApiConfig loadConf(String[] args) throws IOException {
        var objectMapper = new ObjectMapper();
        ApiConfig apiConfig = null;

        if (System.getenv().containsKey("API_CONF")) {
            String conf = System.getenv("API_CONF");
            apiConfig = objectMapper.readValue(conf, ApiConfig.class);
        }

        if (apiConfig == null) {
            if (args.length > 0) {
                apiConfig = objectMapper.readValue(new File(args[0]), ApiConfig.class);
            }
        }

        return apiConfig;
    }
}
