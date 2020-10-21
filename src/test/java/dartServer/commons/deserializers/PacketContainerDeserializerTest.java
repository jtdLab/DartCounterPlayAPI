package dartServer.commons.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dartServer.commons.JsonManager;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.packets.incoming.requests.CreateGamePacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketContainerDeserializerTest {

    @Test
    public void testDeserialize() {
        PacketContainerDeserializer deserializer = new PacketContainerDeserializer();
        Gson gson = JsonManager.getGson();

        PacketContainer original = new PacketContainer(new CreateGamePacket());
        JsonElement jsonElement2 = gson.toJsonTree(original);
        PacketContainer deserialized = deserializer.deserialize(jsonElement2, null, null);
        assertEquals(original, deserialized);
    }

}