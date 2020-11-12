package websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Client extends WebSocketClient {
    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected!");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("New message! -> " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed!  --  " + code + " | " + reason + " | " + remote);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("onError");
        ex.printStackTrace();
    }

    @Override
    public PingFrame onPreparePing(WebSocket conn) {
        PingFrame pingFrame = new PingFrame();
        pingFrame.setPayload(str_to_bb("I'm here!", StandardCharsets.UTF_8));
        return pingFrame;
    }

    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {
        System.out.println("onWebsocketPing -> " + bb_to_str(f.getPayloadData(), StandardCharsets.UTF_8));
        super.onWebsocketPing(conn, f);
    }

    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        System.out.println("onWebsocketPong -> " + bb_to_str(f.getPayloadData(), StandardCharsets.UTF_8));
        super.onWebsocketPong(conn, f);
    }

    public static ByteBuffer str_to_bb(String msg, Charset charset){
        return ByteBuffer.wrap(msg.getBytes(charset));
    }

    public static String bb_to_str(ByteBuffer buffer, Charset charset){
        byte[] bytes;
        if(buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
    }
}
