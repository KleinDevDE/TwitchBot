package de.kleindev.twitchbot.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.kleindev.libsec.crypto.CryptoFields;
import de.kleindev.twitchbot.helpers.GsonHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Packet extends CryptoFields {
    private PacketType packetType;
    private UUID sessionID;
    private String hardwareID;

    public static boolean isValidPacket(String message){
        try {
            new Gson().fromJson(message, Packet.class);
            return true;
        } catch (JsonSyntaxException e){
            return false;
        }
    }

    public static Packet getPacket(String string){
        return GsonHelper.fromJson(string, Packet.class);
    }

    public String getSendableString(){
        encrypt();
        return GsonHelper.toJson(this);
    }
}
