package networking.connection;

public interface Connection {

    void start();

    String receiveCommand();

    void close();

    String getSubscriberDetails();

    String getServerHost();

    Integer getServerPort();

}
