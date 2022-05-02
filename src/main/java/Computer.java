import networking.connection.Connection;
import networking.connection.SocketConnection;
import networking.subscribtion.NullSubscription;
import networking.subscribtion.Subscription;
import util.Command;
import util.log.Logger;
import util.log.LoggerImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Computer {

    private static final Integer UPPER_LIMIT = 100;
    private static final Logger logger = Logger.getLoggerInstance(Computer.class.getName());
    private static final Logger infoLogger = new LoggerImpl(Computer.class.getName());

    private Connection connection;
    private Subscription subscription = NullSubscription.getInstance();
    private boolean started = false;
    private Thread subscriberMessageThread;

    public Computer(String host, Integer port) {
        connection = new SocketConnection(host, port);
    }

    public void subscribe(Subscription subscription) throws IOException {
        this.subscription = subscription;
    }

    public void start() {
        logger.log("\tStarting the computer...");
        started = true;
        connection.start();
        subscriberMessageThread = new Thread(this::receiveSubscriberMessage);
        subscriberMessageThread.start();
        logger.log("\tComputer started!");
    }

    private void receiveSubscriberMessage() {
        while (true) {
            try {
                int number = Integer.parseInt(connection.receiveCommand());
                infoLogger.log("Got the number: %d from %s", number, connection.getSubscriberDetails());
                if (number < UPPER_LIMIT) {
                    infoLogger.log("Sending %d to %s:%d", number + 1, subscription.getHost(), subscription.getPort());
                    subscription.sendMessage(number + 1 + "");
                } else {
                    infoLogger.log("The number reached the upper limit(%d)\n", number);
                }
            } catch (NumberFormatException numberFormatException) {
                logger.log("Didn't recognized the command, thrown a numberFormatException: " + numberFormatException.getMessage());
            }
        }
    }

    public void listenToCommands(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while (true) {
            if (scanner.hasNext()) {
                String command = scanner.nextLine();
                handleCommands(command);
            }
        }
    }

    private void handleCommands(String command) {
        if (Command.isCommand(command)) {
            if (Command.isForward(command)) {
                if (started) {
                    String message = Command.extractNumberFromForwardCommand(command) + 1 + "";
                    subscription.sendMessage(message);
                }
            } else if (Command.isKill(command)) {
                kill();
            } else if (Command.isStartCommand(command)) {
                if (!started) {
                    new Thread(this::start).start();
                    logger.log("Started on other thread.");
                } else {
                    logger.log("Already started!");
                }
            } else if (Command.isSubscribeCommand(command)) {
                subscription = Command.getSubscription(command);
                subscription.setDetails(String.format("%s:%d", connection.getServerHost(), connection.getServerPort()));
            } else {
                logger.log("Unknown command: \"" + command + "\"");
            }
        } else {
            logger.log("Not a command!");
        }
    }

    private void kill() {
        logger.log("Killing the process...");
        logger.log("\tClosing the subscription and connection...");
        subscription.close();
        if (started) {
            subscriberMessageThread.stop();
            logger.log("\tClosing the connection...");
            connection.close();
        }
        logger.log("\tExiting...");
        System.exit(0);
    }

}
