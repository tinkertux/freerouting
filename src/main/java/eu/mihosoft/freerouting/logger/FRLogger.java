package eu.mihosoft.freerouting.logger;

import eu.mihosoft.freerouting.FreeRouting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class FRLogger {
    public static Logger logger = LogManager.getLogger(FreeRouting.class);

    private static DecimalFormat performanceFormat = new DecimalFormat("0.00");

    private static HashMap<Integer, Instant> perfData = new HashMap<Integer, Instant>();

    public static void traceEntry(String perfId)
    {
        perfData.put(perfId.hashCode(), java.time.Instant.now());
    }

    public static void traceExit(String perfId)
    {
        var timeElapsed = Duration.between(perfData.get(perfId.hashCode()), java.time.Instant.now()).toMillis();

        perfData.remove(perfId.hashCode());
        if (timeElapsed < 0) {
            timeElapsed = 0;
        }
        logger.trace("Method '" + perfId + "' was performed in " + performanceFormat.format(timeElapsed/1000.0) + " seconds.");
    }
}
