import util.log.Logger;
import util.log.LoggerImpl;

public class Application {

    private static final Logger logger = new LoggerImpl(Application.class.getName());

    public static void main(String[] args) {
        if (args.length < 2) {
            logger.log("Bye bye");
        }
        String host = args[0];
        Integer port = Integer.parseInt(args[1]);
        Computer computer = new Computer(host, port);
        computer.listenToCommands(System.in);
    }

}
