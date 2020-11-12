package de.kleindev.twitchbot.client.helpers;

import com.google.gson.*;
import de.kleindev.twitchbot.client.websocket.Packet;
import lombok.SneakyThrows;

import java.lang.reflect.Type;

public class PacketDeSerializer implements JsonDeserializer<Packet> {
    @SneakyThrows
    @Override
    public Packet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //TODO Make this dynamic
        Class<?> clazz = Class.forName("de.kleindev.twitchbot.client."+json.getAsJsonObject().get("type").getAsString());

        return new Gson().fromJson(
                json.getAsJsonObject().get("data"),
                (Type) clazz
        );
    }
}
