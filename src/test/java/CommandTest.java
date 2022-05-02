import org.junit.Test;
import util.Command;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class CommandTest {

    private static final Integer numberOfTests = 100000;
    private static final Random randomGenerator = new Random();
    private static final String COMMAND_PREFIX = "#command:";

    @Test
    public void testIsCommand_success() {
        for (int i = 0; i < numberOfTests; i++) {
            boolean result = Command.isCommand(COMMAND_PREFIX + getSpaces() + UUID.randomUUID());
            assertTrue(result);
        }
    }

    @Test
    public void testIsForward_success_returnsTrue() {
        for (int i = 0; i < numberOfTests; i++) {
            int number = getPositiveInteger();
            String command = COMMAND_PREFIX + getSpaces() + "forward" + getSpaces() + number;
            assertTrue(Command.isForward(command));
        }
    }

    @Test
    public void testExtractNumberFromForwardCommand_success_returnsTrue() {
        for (int i = 0; i < numberOfTests; i++) {
            int number = getPositiveInteger();
            String command = COMMAND_PREFIX + getSpaces() + "forward" + getSpaces() + number;
            assertTrue(Command.isForward(command));
            assertEquals(number, Command.extractNumberFromForwardCommand(command));
        }
    }

    @Test
    public void testIsKill_success_returnsTrue() {
        for (int i = 0; i < numberOfTests; i++) {
            String command = COMMAND_PREFIX + getSpaces() + "kill" + UUID.randomUUID();
            assertTrue(Command.isKill(command));
        }
    }

    @Test
    public void testIsCommand_returnsFalse() {
        for (int i = 0; i < numberOfTests; i++) {
            boolean result = Command.isCommand(UUID.randomUUID() + getSpaces() + UUID.randomUUID());
            assertFalse(result);
        }
    }

    @Test
    public void testIsStartCommand_success() {
        for (int i = 0; i < numberOfTests; i++) {
            String command = COMMAND_PREFIX + getSpaces() + "subscribe" + getSpaces() + String.format("127.%d.%d.%d %d",
                    getPositiveInteger() % 256, getPositiveInteger() % 256, getPositiveInteger() % 256, getPositiveInteger() % 65000);
            assertTrue(Command.isSubscribeCommand(command));
        }
    }

    @Test
    public void testIsForward_returnsFalse() {
        for (int i = 0; i < numberOfTests; i++) {
            int number = getPositiveInteger();
            String command = COMMAND_PREFIX + getSpaces() + UUID.randomUUID() + getSpaces() + number;
            assertFalse(Command.isForward(command));
        }
    }

    @Test
    public void testIsKill_returnsFalse() {
        for (int i = 0; i < numberOfTests; i++) {
            String command = COMMAND_PREFIX + getSpaces() + UUID.randomUUID();
            assertFalse(Command.isKill(command));
        }
    }


    private String getSpaces() {
        boolean haveSpaces = getPositiveInteger() % 100 > 30;
        if (!haveSpaces) {
            return "";
        }
        int numberOfSpaces = getPositiveInteger() % 10 + 1;
        String result = "";
        for (int i = 0; i < numberOfSpaces; i++) {
            result += " ";
        }
        return result;
    }

    private int getPositiveInteger() {
        return Math.abs(randomGenerator.nextInt());
    }

}