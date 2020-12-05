package dartServer.commons;

import com.google.gson.Gson;
import dartServer.commons.parsing.JsonManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonManagerTest {

    @Test
    public void testGetGson() {
        Gson gson = JsonManager.getGson();

        // serialize Nulls
        assertTrue(gson.serializeNulls());

        // timestamp format yyyy-MM-dd HH:mm:ss.SSS
        // TODO
        // typeAdapters PacketTypeSerializer, PacketContainerDeserializer
        // TODO
    }

}