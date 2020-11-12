package old.packets;

import com.google.gson.Gson;
import org.java_websocket.WebSocket;

import java.util.Base64;
import java.util.UUID;

public abstract class Packet {
    private final String clazz = getClass().getName();
    private byte packetSize;
    private byte checkDigit;
    protected UUID sessionID; //TODO get clientID straight from the globalclass
    //TODO @clientID may change it so sessionID or something else to better handling outgoing packets

    public UUID getSessionID() {
        return sessionID;
    }

    public byte getCheckDigit() {
        return checkDigit;
    }

    public byte getPacketSize() {
        return packetSize;
    }

    public String getClazz() {
        return clazz;
    }

    public void setSessionID(UUID sessionID) {
        this.sessionID = sessionID;
    }

    public void prepare() {
        this.checkDigit = calculateCheckDigit();
        this.packetSize = calculateObjectSize();
    }

    public void send(WebSocket webSocket){
        prepare();
        webSocket.send(toString());
    }

    public boolean isPackageValid(byte checkDigit) {
        return this.checkDigit == checkDigit && checkDigit == calculateCheckDigit();
    }

    private byte calculateCheckDigit() {
        return 01;
    }

    private byte calculateObjectSize() {
        return 02;
    }

    @Override
    public String toString() {
        return new String(Base64.getEncoder().encode(new Gson().toJson(this).getBytes()));
    }
}
