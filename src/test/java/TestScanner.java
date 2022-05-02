import util.Command;
import util.log.Logger;
import util.log.LoggerImpl;

import java.util.Scanner;

public class TestScanner {

    private static final Logger logger = new LoggerImpl(TestScanner.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (Command.isCommand(line)) {
                    if (Command.isForward(line)) {
                        logger.log("Forwarding " + Command.extractNumberFromForwardCommand(line));
                    } else if (Command.isKill(line)) {
                        break;
                    } else {
                        logger.log("Unknown util.Command!");
                    }
                } else {
                    logger.log("Read from scanner: " + line);
                }
            }
        }
        logger.log("Exiting...");
    }

}
