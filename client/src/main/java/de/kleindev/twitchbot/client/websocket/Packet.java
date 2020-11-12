package de.kleindev.twitchbot.client.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.kleindev.libsec.crypto.CryptoFields;
import de.kleindev.twitchbot.client.helpers.GsonHelper;

public class Packet extends CryptoFields {
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
