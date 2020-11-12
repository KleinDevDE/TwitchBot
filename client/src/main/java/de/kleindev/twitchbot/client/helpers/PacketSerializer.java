package de.kleindev.twitchbot.client.helpers;

import com.google.gson.*;
import de.kleindev.twitchbot.client.websocket.Packet;

import java.lang.reflect.Type;

public class PacketSerializer implements JsonSerializer<Packet> {

    @Override
    public JsonElement serialize(Packet object, Type type, JsonSerializationContext ctx ) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", convertClassName(object.getClass().getName()));
        wrapper.add("data", new Gson().toJsonTree(object));
        return wrapper;
    }

    private String convertClassName(String className){
        return className
                .replace("de.kleindev.twitchbot.client.", "")
                .replace("de.kleindev.twitchbot.server.", "");
    }
}