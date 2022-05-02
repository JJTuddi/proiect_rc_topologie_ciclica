package networking.subscribtion;

import util.log.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketSubscription implements Subscription {

    private static final Logger logger = Logger.getLoggerInstance(SocketSubscription.class.getName());

    private Socket socket;
    private DataOutputStream out;
    private boolean sentDetails = false;

    public SocketSubscription(String host, Integer port) {
        try {
            socket = new Socket(host, port);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            logger.log("Error while opening a socket in networking.subscribtion.SocketSubscription class: " + exception.getMessage());
        }
    }

    @Override
    public void sendMessage(String message) {
        logger.log("Sending %s to %s:%d%n", message, getHost(), getPort());
        try {
            if (!sentDetails) {
                out.writeUTF("Anonymous");
            }
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            out.close();
            socket.close();
        } catch (IOException exception) {
            logger.log("Error while closing the networking.subscribtion.SocketSubscription: " + exception.getMessage());
        }
    }

    @Override
    public int getPort() {
        return socket.getPort();
    }

    @Override
    public String getHost() {
        return socket.getInetAddress().toString();
    }

    @Override
    public void setDetails(String details) {
        sentDetails = true;
        try {
            out.writeUTF(details);
        } catch (IOException e) {
            logger.log("Error encountered: " + e.getMessage());
        }
    }

}
