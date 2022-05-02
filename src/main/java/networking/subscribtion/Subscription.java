package networking.subscribtion;

public interface Subscription {

    void sendMessage(String message);

    void close();

    int getPort();

    String getHost();

    void setDetails(String details);

}
