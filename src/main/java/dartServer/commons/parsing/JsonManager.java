package dartServer.commons.parsing;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dartServer.commons.parsing.deserializers.PacketContainerDeserializer;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.packets.PacketType;
import dartServer.commons.parsing.serializers.PacketTypeSerializer;

/**
 * Creates JSON objects
 */
public class JsonManager {

    private JsonManager() {
        throw new IllegalStateException("Can not be instantiated!");
    }

    public static Gson getGson() {
        GsonBuilder gson = new GsonBuilder();

        gson.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // Serialize nulls
        gson.serializeNulls();

        // Serializers
        gson.registerTypeAdapter(PacketType.class, new PacketTypeSerializer());

        // Deserializers
        gson.registerTypeAdapter(PacketContainer.class, new PacketContainerDeserializer());

        return gson.create();
    }

}
