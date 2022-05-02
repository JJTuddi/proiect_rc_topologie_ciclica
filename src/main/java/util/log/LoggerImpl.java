package util.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerImpl extends Logger {

    private String className;
    private static final String format = "[LOG] [%10s] [%s] -- %s";
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("hh:mm:ss - dd/MM/yyyy");

    public LoggerImpl(String className) {
        this.className = className;
    }

    @Override
    public void log(String message) {
        String currentTime = dateTimeFormat.format(LocalDateTime.now());
        String printedMessage = String.format(format, className, currentTime, message);
        System.out.println(printedMessage);
    }

    @Override
    public void log(String messageFormat, Object... obj) {
        String currentTime = dateTimeFormat.format(LocalDateTime.now());
        String message = String.format(messageFormat, obj);
        String printedMessage = String.format(format, className, currentTime, message);
        System.out.println(printedMessage);
    }

}
