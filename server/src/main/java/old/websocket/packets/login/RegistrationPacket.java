package old.websocket.packets.login;

import old.websocket.packets.Packet;

public class RegistrationPacket extends Packet {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        //TODO decrypt
        return username;
    }

    public String getEmail() {
        //TODO decrypt
        return email;
    }

    public String getPassword() {
        //TODO decrypt
        return password;
    }

    public void setUsername(String username) {
        //TODO encrypt
        this.username = username;
    }

    public void setEmail(String email) {
        //TODO encrypt
        this.email = email;
    }

    public void setPassword(String password) {
        //TODO encrypt
        this.password = password;
    }
}
