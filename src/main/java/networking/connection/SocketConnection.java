package networking.connection;

import util.log.Logger;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketConnection implements Connection {

    private final static Integer DEFAULT_BACKLOG = 16;
    private final static Logger logger = Logger.getLoggerInstance(SocketConnection.class.getName());

    private final String serverHost;
    private final Integer serverPort;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream in;
    private String subscriberDetails;

    public SocketConnection(String serverHost, Integer serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public void start() {
        try {
            logger.log("\tStarting the Connection to: %s:%d\n", serverHost, serverPort);
            serverSocket = new ServerSocket(serverPort, DEFAULT_BACKLOG, getHostAddress());
            logger.log("\tWaiting for someone to subscribe...");
            socket = serverSocket.accept();
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                subscriberDetails = in.readUTF();
            } catch (IOException exception) {
                logger.log("Error encountered: ", exception.getMessage());
            }
            logger.log("\tSomeone subscribed -> Connection started!");
        } catch (IOException unknownHostException) {
            throw new RuntimeException(unknownHostException);
        }
    }

    @Override
    public String receiveCommand() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSubscriberDetails() {
        return subscriberDetails;
    }

    public String getServerHost() {
        return serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    @Override
    public void close() {
        try {
            in.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InetAddress getHostAddress() throws UnknownHostException {
        return InetAddress.getByName(serverHost);
    }

}
