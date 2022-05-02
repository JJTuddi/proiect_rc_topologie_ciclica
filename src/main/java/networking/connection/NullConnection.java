package networking.connection;

public class NullConnection implements Connection {

    @Override
    public void start() {

    }

    @Override
    public String receiveCommand() {
        return "";
    }

    @Override
    public void close() {

    }

    @Override
    public String getSubscriberDetails() {
        return null;
    }

    @Override
    public String getServerHost() {
        return null;
    }

    @Override
    public Integer getServerPort() {
        return null;
    }

}
