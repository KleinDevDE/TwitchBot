package old.utils.connections;


public abstract class Connection {
    public abstract void connect();
    public abstract void disconnect();
    public abstract boolean isAlive();
}
