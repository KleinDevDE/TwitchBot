package old.websocket.endpoints;

import old.utils.managers.SessionManager;
import old.utils.managers.UserManager;
import old.websocket.packets.Packet;
import old.websocket.packets.login.*;
import old.websocket.packets.general.WrongEndpointPacket;
import old.websocket.packets.login.error.EmailAlreadyRegisteredPacket;
import old.websocket.packets.login.error.UsernameAlreadyRegisteredPacket;
import old.websocket.packets.login.error.WrongPasswordPacket;
import org.java_websocket.WebSocket;

import java.util.UUID;

@ServerEndpoint(endpoint = "login")
public class LoginEndpoint extends WebSocketEndpoint{
    @Override
    public void onMessage(Packet packet, WebSocket webSocket) {
        if (packet instanceof LoginPacket){
            LoginPacket loginPacket = (LoginPacket) packet;
            UUID sessionID = UserManager.login(loginPacket.getUsername(), loginPacket.getPassword());
            if(sessionID != null){ //TODO check credentials
                LoginConfirmedPacket loginConfirmedPacket = new LoginConfirmedPacket();
                SessionManager.registerSession(sessionID, loginPacket.getUsername(), webSocket);
                loginConfirmedPacket.setSessionID(UUID.randomUUID());
                loginConfirmedPacket.send(webSocket);
            } else {
                WrongPasswordPacket wrongPasswordPacket = new WrongPasswordPacket();
                wrongPasswordPacket.setSessionID(packet.getSessionID());
                wrongPasswordPacket.send(webSocket);
            }
        } else if(packet instanceof RegistrationPacket){
            RegistrationPacket registrationPacket = (RegistrationPacket) packet;
            if (!UserManager.usernameExists(registrationPacket.getUsername())){
                if (UserManager.emailExists(registrationPacket.getEmail())) {
                    EmailAlreadyRegisteredPacket emailAlreadyRegisteredPacket = new EmailAlreadyRegisteredPacket(registrationPacket.getEmail());
                    emailAlreadyRegisteredPacket.send(webSocket);
                    return;
                }
                RegistrationConfirmedPacket registrationConfirmedPacket = new RegistrationConfirmedPacket();
                registrationConfirmedPacket.setSessionID(register(registrationPacket, webSocket));
                registrationConfirmedPacket.setUsername(registrationPacket.getUsername());
                registrationPacket.send(webSocket);
            } else {
                UsernameAlreadyRegisteredPacket usernameAlreadyRegisteredPacket = new UsernameAlreadyRegisteredPacket(registrationPacket.getUsername());
                usernameAlreadyRegisteredPacket.send(webSocket);
            }
        } else if(packet instanceof RecoverAccountPacket){
            RecoverAccountPacket recoverAccountPacket = (RecoverAccountPacket) packet;  
            //TODO Recover account!
        } else {
            new WrongEndpointPacket(this).send(webSocket);
        }
    }

    private UUID register(RegistrationPacket registrationPacket, WebSocket webSocket){
        return UserManager.register(registrationPacket.getUsername(), registrationPacket.getEmail(), registrationPacket.getPassword());
    }
}
