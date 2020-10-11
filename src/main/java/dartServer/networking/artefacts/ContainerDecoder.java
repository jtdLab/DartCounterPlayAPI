package dartServer.networking.artefacts;

import com.google.gson.*;
import dartServer.networking.artefacts.requests.*;
import dartServer.networking.artefacts.responses.*;

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

            JsonObject payload;
            if(!jsonObject.get("payload").isJsonNull()) {
                payload = jsonObject.getAsJsonObject("payload");
                switch (type) {
                    case "authRequest":
                        return new Container(type, gson.fromJson(payload, AuthRequest.class));
                    case "cancelGameRequest":
                        return new Container(type, gson.fromJson(payload, CancelGameRequest.class));
                    case "createGameRequest":
                        return new Container(type, gson.fromJson(payload, CreateGameRequest.class));
                    case "doThrowRequest":
                        return new Container(type, gson.fromJson(payload, DoThrowRequest.class));
                    case "joinGameRequest":
                        return new Container(type, gson.fromJson(payload, JoinGameRequest.class));
                    case "startGameRequest":
                        return new Container(type, gson.fromJson(payload, StartGameRequest.class));
                    case "undoThrowRequest":
                        return new Container(type, gson.fromJson(payload, UndoThrowRequest.class));
                    case "authResponse":
                        return new Container(type, gson.fromJson(payload, AuthResponse.class));
                    case "cancelGameResponse":
                        return new Container(type, gson.fromJson(payload, CancelGameResponse.class));
                    case "createGameResponse":
                        return new Container(type, gson.fromJson(payload, CreateGameResponse.class));
                    case "doThrowResponse":
                        return new Container(type, gson.fromJson(payload, DoThrowResponse.class));
                    case "joinGameResponse":
                        return new Container(type, gson.fromJson(payload, JoinGameResponse.class));
                    case "startGameResponse":
                        return new Container(type, gson.fromJson(payload, StartGameResponse.class));
                    case "undoThrowResponse":
                        return new Container(type, gson.fromJson(payload, UndoThrowResponse.class));
                    default:
                       return null;
                }
            } else {
                return new Container(type, null);
            }
        }
    }
}
