package dartServer.networking.handlers.codec;

import com.google.gson.*;
import dartServer.networking.artefacts.Container;
import dartServer.networking.artefacts.requests.*;
import dartServer.networking.artefacts.responses.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Type;


public class ContainerDecoder extends SimpleChannelInboundHandler<String> {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Container.class, new ContainerDecoder.ContainerAdapter())
            .create();


    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            Container container = decode(msg);
            ctx.fireChannelRead(container);
        } catch (Exception e) {
            // TODO log decoder-error
        }
    }

    private Container decode(String json) throws Exception {
        Container container = gson.fromJson(json, Container.class);
        if(container != null) {
            return container;
        }

        throw new Exception("Cant decode String: " + json);
    }

    private static class ContainerAdapter implements JsonDeserializer<Container> {

        @Override
        public Container deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            Gson gson = new Gson();

            String type = jsonObject.get("type").getAsString();

            JsonObject packet;
            if(!jsonObject.get("packet").isJsonNull()) {
                packet = jsonObject.getAsJsonObject("packet");
                switch (type) {
                    case "authRequest":
                        return new Container(type, gson.fromJson(packet, AuthRequest.class));
                    case "cancelGameRequest":
                        return new Container(type, gson.fromJson(packet, CancelGameRequest.class));
                    case "createGameRequest":
                        return new Container(type, gson.fromJson(packet, CreateGameRequest.class));
                    case "doThrowRequest":
                        return new Container(type, gson.fromJson(packet, DoThrowRequest.class));
                    case "joinGameRequest":
                        return new Container(type, gson.fromJson(packet, JoinGameRequest.class));
                    case "leaveGameRequest":
                        return new Container(type, gson.fromJson(packet, LeaveGameRequest.class));
                    case "startGameRequest":
                        return new Container(type, gson.fromJson(packet, StartGameRequest.class));
                    case "undoThrowRequest":
                        return new Container(type, gson.fromJson(packet, UndoThrowRequest.class));
                    case "authResponse":
                        return new Container(type, gson.fromJson(packet, AuthResponse.class));
                    case "cancelGameResponse":
                        return new Container(type, gson.fromJson(packet, CancelGameResponse.class));
                    case "createGameResponse":
                        return new Container(type, gson.fromJson(packet, CreateGameResponse.class));
                    case "doThrowResponse":
                        return new Container(type, gson.fromJson(packet, DoThrowResponse.class));
                    case "joinGameResponse":
                        return new Container(type, gson.fromJson(packet, JoinGameResponse.class));
                    case "leaveGameResponse":
                        return new Container(type, gson.fromJson(packet, LeaveGameResponse.class));
                    case "startGameResponse":
                        return new Container(type, gson.fromJson(packet, StartGameResponse.class));
                    case "undoThrowResponse":
                        return new Container(type, gson.fromJson(packet, UndoThrowResponse.class));
                    default:
                       return null;
                }
            } else {
                return new Container(type, null);
            }
        }
    }
}
