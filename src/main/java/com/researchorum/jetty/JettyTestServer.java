package com.researchorum.jetty;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * @author pbathala
 */
public class JettyTestServer {

    private static Logger logger = Logger.getLogger(JettyTestServer.class);
    private static final int DEFAULT_HTTP_PORT = 8080;
    private static final String WAR_LOCATION = "src/main/webapp";

    static {
        ConsoleAppender console = new ConsoleAppender();
        final String PATTERN = "%p | (%t) | %l | %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        //add appender to any Logger (here is root)
        Logger.getRootLogger().addAppender(console);
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(DEFAULT_HTTP_PORT);
            WebAppContext webAppContext = new WebAppContext();
            webAppContext.setContextPath("/");
            webAppContext.setWar(WAR_LOCATION);

            webAppContext.setServer(server);
            server.setHandler(webAppContext);
            server.start();
        } catch (Exception e) {
            logger.error("Failed to start Jetty server", e);
        }
    }
}