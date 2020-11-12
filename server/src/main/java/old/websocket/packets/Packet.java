package old.websocket.packets;

import com.google.gson.Gson;
import de.kleindev.libsec.crypto.CryptoFields;
import old.utils.managers.SessionManager;
import org.java_websocket.WebSocket;

import java.util.Base64;
import java.util.UUID;

public abstract class Packet extends CryptoFields {
    private final String clazz = getClass().getName();
    private byte packetSize;
    private byte checkDigit;
    protected UUID sessionID;

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

    public void send(){
        prepare();
        SessionManager.getSession(sessionID).getWebSocket().send(toString());
    }

    public boolean isPackageValid(byte checkDigit) {
        return this.checkDigit == checkDigit && checkDigit == calculateCheckDigit();
    }

    private byte calculateCheckDigit() {
        return 1;
    }

    private byte calculateObjectSize() {
        return 2;
    }

    public String toJSON(){
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return new String(Base64.getEncoder().encode(new Gson().toJson(this).getBytes()));
    }
}
