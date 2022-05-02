package networking.subscribtion;

public class NullSubscription implements Subscription {

    private static final NullSubscription instance = new NullSubscription();

    public static NullSubscription getInstance() {
        return instance;
    }

    private NullSubscription() {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void close() {

    }

    @Override
    public int getPort() {
        return -1;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void setDetails(String details) {

    }

}
