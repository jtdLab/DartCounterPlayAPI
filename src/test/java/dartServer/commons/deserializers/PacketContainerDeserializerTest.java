package dartServer.commons.deserializers;

import com.google.gson.*;
import dartServer.commons.JsonManager;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.packets.incoming.requests.CreateGamePacket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketContainerDeserializerTest {

    @Test
    public void testDeserialize() {
        PacketContainerDeserializer deserializer = new PacketContainerDeserializer();
        Gson gson = new Gson();

        // invalid timestamp
        // timestamp null
        String timestamp = "null";
        JsonElement jsonElement1 = gson.fromJson(" {\"payloadType\":\"createGame\",\"payload\":{},\"timestamp\":\""+ timestamp + "\"}", JsonElement.class);
        Assertions.assertThrows(JsonParseException.class, () -> {
            deserializer.deserialize(jsonElement1,null, null);
        });

        // wrong format
        timestamp = "31.12.2020 9:32";
        JsonElement jsonElement2 = gson.fromJson(" {\"payloadType\":\"createGame\",\"payload\":{},\"timestamp\":\""+ timestamp + "\"}", JsonElement.class);
        Assertions.assertThrows(JsonParseException.class, () -> {
            deserializer.deserialize(jsonElement2,null, null);
        });

        // TODO test this
     /*   // timestamp future
        timestamp = "3000-01-01 01:00:00.44";
        JsonElement jsonElement3 = gson.fromJson(" {\"payloadType\":\"createGame\",\"payload\":{},\"timestamp\":\""+ timestamp + "\"}", JsonElement.class);
        Assertions.assertThrows(JsonParseException.class, () -> {
            deserializer.deserialize(jsonElement3,null, null);
        });*/


        // invalid payloadType
        // payloadType null
        String payloadType = "null";
        JsonElement jsonElement4 = gson.fromJson("{\"payloadType\":\"" +payloadType + "\",\"payload\":{},\"timestamp\":\"2020-10-17 03:38:16.44\"}", JsonElement.class);
        Assertions.assertThrows(JsonParseException.class, () -> {
            deserializer.deserialize(jsonElement4,null, null);
        });

        // payloadType not existing
        payloadType = "notExistingPacketType";
        JsonElement jsonElement5 = gson.fromJson("{\"payloadType\":\"" +payloadType + "\",\"payload\":{},\"timestamp\":\"2020-10-17 03:38:16.44\"}", JsonElement.class);
        Assertions.assertThrows(JsonParseException.class, () -> {
            deserializer.deserialize(jsonElement5,null, null);
        });


        // invalid payload
        // TODO test this
       /* // payload null
        String payload = "null";
        JsonElement jsonElement6 = gson.fromJson(" {\"payloadType\":\"createGame\",\"payload\":" + payload + ",\"timestamp\":\"2020-10-17 03:38:16.44\"}", JsonElement.class);
        Assertions.assertThrows(JsonParseException.class, () -> {
            deserializer.deserialize(jsonElement6,null, null);
        });

        // payload not existing
        payload = "{\"notExisting\":\"packet\"}";
        JsonElement jsonElement7 = gson.fromJson(" {\"payloadType\":\"createGame\",\"payload\":" + payload + ",\"timestamp\":\"2020-10-17 03:38:16.44\"}", JsonElement.class);
        Assertions.assertThrows(JsonParseException.class, () -> {
            deserializer.deserialize(jsonElement7,null, null);
        });*/


        // valid packet
        gson = JsonManager.getGson();
        PacketContainer original = new PacketContainer(new CreateGamePacket());
        JsonElement jsonElement8 = gson.toJsonTree(original);
        PacketContainer deserialized = deserializer.deserialize(jsonElement8, null, null);
        assertEquals(original, deserialized);
    }

}