package dartServer.networking.artefacts;

import dartServer.networking.artefacts.requests.AuthRequest;
import com.google.gson.*;

import java.lang.reflect.Type;


public class ContainerDecoder {

    private static final Gson gson = gsonInstance();

    public static Container decode(String json) {
        try {
            return gson.fromJson(json, Container.class);
        } catch (JsonParseException e) {
            // TODO invalid json received add log
        }

        return null;
    }

    private static Gson gsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Container.class, new ContainerDecoder.ContainerAdapter());
        return gsonBuilder.create();
    }

    private static class ContainerAdapter implements JsonDeserializer<Container> {

        @Override
        public Container deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            Gson gson = new Gson();

            String type = jsonObject.get("type").getAsString();
            JsonObject payload = jsonObject.getAsJsonObject("payload");


            switch (type) {
                case "authRequest":
                    return new Container(type, gson.fromJson(payload, AuthRequest.class));
            }

            return null;
        }
    }
}
