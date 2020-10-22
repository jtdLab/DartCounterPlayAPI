package dartServer.commons.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dartServer.commons.JsonManager;
import dartServer.commons.packets.Packet;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.packets.PacketType;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Custom {@link JsonDeserializer} for a {@link PacketContainer}
 */
public class PacketContainerDeserializer implements JsonDeserializer {

    /**
     * {@inheritDoc}
     */
    @Override
    public PacketContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // If JSON does not contain fields payloadType or payload or timestamp throw JsonParseException
        if (!json.getAsJsonObject().has("payloadType") || !json.getAsJsonObject().has("payload") || !json.getAsJsonObject().has("timestamp")) {
            throw new JsonParseException("Missing required fields.");
        }

        // If payloadType is invalid throw JsonParseException
        PacketType payloadType = PacketType.forName(jsonObject.get("payloadType").getAsString());
        if (payloadType == null) {
            throw new JsonParseException("Invalid payload type.");
        }

        // Deserialize the packet
        Packet p = JsonManager.getGson().fromJson(json.getAsJsonObject().get("payload"), TypeToken.get(payloadType.getPacketClass()).getType());

        PacketContainer container = new PacketContainer(p);

        // If timestamp is invalid throw JsonParseException

        Date timestamp = null;
        try {
             timestamp = JsonManager.getGson().fromJson(json.getAsJsonObject().get("timestamp"), Date.class);
        } catch (JsonSyntaxException e) {
            // invalid timestamp
        }

        if (timestamp == null) {
            throw new JsonParseException("Invalid timestamp format.");
        }

        container.setTimestamp(timestamp);

        // Return a new container containing that packet
        return container;
    }
}
