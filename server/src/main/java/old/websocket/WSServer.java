package old.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import old.utils.managers.SessionManager;
import old.websocket.endpoints.WebSocketEndpoint;
import old.utils.managers.EndpointManager;
import old.websocket.packets.Packet;
import old.websocket.packets.login.LoginPacket;
import old.websocket.packets.login.RecoverAccountPacket;
import old.websocket.packets.login.RegistrationPacket;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.framing.PongFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WSServer extends WebSocketServer {
    private Gson gson = new Gson();

    public WSServer( InetSocketAddress address ){
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("onOpen");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("onClose");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("onMessage -> " + message);
        Packet packet = getPacket(message);
        if (!SessionManager.isValidSession(packet.getSessionID())){
            if(!(packet instanceof LoginPacket) && !(packet instanceof RegistrationPacket) && !(packet instanceof RecoverAccountPacket)){
                System.err.println("[SECURITY] Unknown session tried to interact!\nHostname: "+conn.getRemoteSocketAddress().getHostName()+"\nJSON: " + packet.toJSON());
                conn.close();
                return;
            }
        }
        WebSocketEndpoint webSocketEndpoint = EndpointManager.getEndpoint(conn.getResourceDescriptor().replaceFirst("/", ""));
        webSocketEndpoint.onMessage(packet, conn);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("onError");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("onStart");
    }

    @Override
    protected boolean onConnect(SelectionKey key) {
        return super.onConnect(key);
    }

    @Override
    public void onWebsocketHandshakeReceivedAsClient(WebSocket conn, ClientHandshake request, ServerHandshake response) throws InvalidDataException {
        System.out.println("onWebsocketHandshakeReceivedAsClient");
        super.onWebsocketHandshakeReceivedAsClient(conn, request, response);
    }

    @Override
    public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) throws InvalidDataException {
        System.out.println("onWebsocketHandshakeSentAsClient");
        super.onWebsocketHandshakeSentAsClient(conn, request);
    }

    @Override
    public PingFrame onPreparePing(WebSocket conn) {
        return super.onPreparePing(conn);
    }

    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {
        System.out.println("onWebsocketPing -> " + bb_to_str(f.getPayloadData(), StandardCharsets.UTF_8));
        PongFrame pongFrame = new PongFrame();
        pongFrame.setPayload(str_to_bb("Nice!", StandardCharsets.UTF_8));
        conn.sendFrame(pongFrame);
    }

    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        System.out.println("onWebsocketPong -> " + bb_to_str(f.getPayloadData(), StandardCharsets.UTF_8));
        super.onWebsocketPong(conn, f);
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        System.out.println("onWebsocketHandshakeReceivedAsServer -> ResourceDescriptor:" + request.getResourceDescriptor());
        ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);

        if (!request.hasFieldValue("clientID")){
            System.out.println("\"clientID\" is missing");
            throw new InvalidDataException( CloseFrame.POLICY_VALIDATION, "Not accepted!");
        }
        if (!request.hasFieldValue("token")){
            System.out.println("\"token\" is missing");
            throw new InvalidDataException( CloseFrame.POLICY_VALIDATION, "Not accepted!");
        }
        if (!request.hasFieldValue("check")){ //TODO check= sha256(base64(sha256(base64(clientID)+base64(token))))
            System.out.println("\"check\" is missing");
            throw new InvalidDataException( CloseFrame.POLICY_VALIDATION, "Not accepted!");
        }

        if(!EndpointManager.isValidEndpoint(request.getResourceDescriptor().replaceFirst("/", ""))){
            System.out.println("Not valid endpoint");
            throw new InvalidDataException(CloseFrame.BAD_GATEWAY);
        }
        return builder;
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

    public Packet getPacket(String message){
        String json = new String(Base64.getDecoder().decode(message.getBytes()));
        System.out.println("json: " + json);
        //TODO decrypt
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return getPacket(jsonObject);
    }

    public Packet getPacket(JsonObject jsonObject){
        try {
            return (Packet) gson.fromJson(jsonObject, getClass().getClassLoader().loadClass(jsonObject.get("clazz").getAsString().replace("client.packets", "server.websocket.packets")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
