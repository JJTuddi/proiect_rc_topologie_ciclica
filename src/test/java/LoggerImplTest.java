import org.junit.Test;
import util.log.Logger;
import util.log.LoggerImpl;
import util.log.NullLogger;

import static org.junit.Assert.assertTrue;

public class LoggerImplTest {

    @Test
    public void testLogging() {
        Logger logger = Logger.getLoggerInstance(this.getClass().getName());
        assertTrue(logger instanceof LoggerImpl);
        assertTrue(logger == Logger.getLoggerInstance(this.getClass().getName()));
    }

    @Test
    public void seeLoggingMessages() {
        Logger logger = Logger.getLoggerInstance(this.getClass().getName());
        logger.log("Hello");
        logger.log("Hello%s%s%s", ", ", "World", "!");
    }

}
