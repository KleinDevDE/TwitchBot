package old.websocket.packets.login;

import old.websocket.packets.Packet;

public class LoginPacket extends Packet { //implements CryptFields
    private char[] username;
    private char[] password;

    public String getPassword() {
        //TODO decrypt
        return String.valueOf(password);
    }

    public String getUsername() {
        //TODO decrypt
        return String.valueOf(username);
    }

    public void setPassword(String password) {
        //TODO encrypt
        this.password = password.toCharArray();
    }

    public void setUsername(String username) {
        //TODO encrypt
        this.username = username.toCharArray();
    }
}
