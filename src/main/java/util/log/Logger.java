package util.log;

import java.util.HashMap;
import java.util.Map;

public abstract class Logger {

    private static final Map<String, Logger> loggerPool = new HashMap<>();

    public static Logger getLoggerInstance(String className) {
        if (loggerPool.get(className) == null) {
            Logger logger;
            if ("true".equals(System.getProperty("logd"))) {
                logger = new LoggerImpl(className);
            } else {
                logger = new NullLogger(className);
            }
            loggerPool.put(className, logger);
        }
        return loggerPool.get(className);
    }

    public abstract void log(String message);
    public abstract void log(String format, Object ...obj);

}
