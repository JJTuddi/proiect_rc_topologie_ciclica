import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertNotNull;

public class TestAddress {

    private static final String addressFormat = "127.%d.%d.%d";

    private String serverHost;

    @Test
    public void testAddresses_success() throws UnknownHostException {
        for (int i = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {
                for (int k = 1; k < 254; k++) {
                    serverHost = String.format(addressFormat, i, j, k);
                    InetAddress hostAddress = getHostAddress();
                    assertNotNull(hostAddress);
                }
            }
        }
    }

    private InetAddress getHostAddress() throws UnknownHostException {
        return InetAddress.getByName(serverHost);
    }

}
