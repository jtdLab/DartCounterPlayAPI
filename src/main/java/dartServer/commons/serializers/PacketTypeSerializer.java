package dartServer.commons.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dartServer.commons.packets.PacketType;

import java.lang.reflect.Type;

/**
 * Custom {@link JsonSerializer} for a {@link PacketType}
 */
public class PacketTypeSerializer implements JsonSerializer<PacketType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonElement serialize(PacketType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getJsonType());
    }
}
