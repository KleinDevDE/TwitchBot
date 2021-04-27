package de.kleindev.twitchbot.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.kleindev.twitchbot.websocket.Packet;

public class GsonHelper {
    private static Gson gson = null;

    private static void init(){
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeHierarchyAdapter(Packet.class, new PacketSerializer());
        gb.registerTypeHierarchyAdapter(Packet.class, new PacketDeSerializer());
        gson = gb.create();
    }

    public static String toJson(Object src){
        if (gson == null) init();
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> clazz){
        if (gson == null) init();
        try {
            T clazz2 = gson.fromJson(json, clazz);
            return clazz2;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

