package util;

import networking.subscribtion.SocketSubscription;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

    private final static String COMMAND_PREFIX = "#command:";
    private final static String FORWARD_COMMAND_REGEX = COMMAND_PREFIX + "\\s*forward\\s*-?(\\d+)";
    private final static Pattern FORWARD_COMMAND_PATTERN = Pattern.compile(FORWARD_COMMAND_REGEX, Pattern.CASE_INSENSITIVE);
    private final static Pattern KILL_COMMAND_PATTERN = Pattern.compile(COMMAND_PREFIX + "\\s*kill.*", Pattern.CASE_INSENSITIVE);
    private final static Pattern START_COMMAND_PATTERN = Pattern.compile(COMMAND_PREFIX + "\\s*start", Pattern.CASE_INSENSITIVE);
    private final static Pattern SUBSCRIBE_COMMAND_PATTERN = Pattern.compile(COMMAND_PREFIX + "\\s*subscribe\\s*(127\\.\\d{1,4}\\.\\d{1,4}\\.\\d{1,4})\\s+(\\d+)", Pattern.CASE_INSENSITIVE);

    private Command() {

    }

    public static boolean isCommand(String message) {
        return message.startsWith(COMMAND_PREFIX);
    }

    public static boolean isForward(String command) {
        Matcher matcher = FORWARD_COMMAND_PATTERN.matcher(command);
        return matcher.find();
    }

    public static int extractNumberFromForwardCommand(String command) {
        Matcher matcher = FORWARD_COMMAND_PATTERN.matcher(command);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public static boolean isStartCommand(String command) {
        Matcher matcher = START_COMMAND_PATTERN.matcher(command);
        return matcher.find();
    }

    public static boolean isSubscribeCommand(String command) {
        Matcher matcher = SUBSCRIBE_COMMAND_PATTERN.matcher(command);
        return matcher.find();
    }

    public static SocketSubscription getSubscription(String command) {
        Matcher matcher = SUBSCRIBE_COMMAND_PATTERN.matcher(command);
        if (matcher.find()) {
            return new SocketSubscription(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }
        throw new RuntimeException("Couldn't get a subscription!");
    }

    public static boolean isKill(String command) {
        Matcher matcher = KILL_COMMAND_PATTERN.matcher(command);
        return matcher.find();
    }

}
