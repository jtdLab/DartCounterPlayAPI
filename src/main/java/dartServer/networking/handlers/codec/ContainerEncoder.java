package dartServer.networking.handlers.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dartServer.networking.artefacts.Container;
import dartServer.networking.artefacts.Packet;
import dartServer.networking.artefacts.requests.*;
import dartServer.networking.artefacts.responses.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Encodes response-objects
 */
public class ContainerEncoder extends ChannelOutboundHandlerAdapter {

    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if(msg instanceof Packet) {
            try {
                Packet packet = (Packet) msg;
                super.write(ctx, new TextWebSocketFrame(encode(packet)), promise);
            } catch (Exception e) {
                // TODO log
                e.printStackTrace();
            }
        } else {
            try {
                super.write(ctx, msg, promise);
            } catch (Exception e) {
                // TODO log
                e.printStackTrace();
            }
        }
    }

    private String encode(Packet packet) throws Exception {
        if(packet == null) {
            throw new Exception("Can't encode payload: null");
        }

        Container container;
        if (packet instanceof AuthRequest) {
            container = new Container("authRequest", packet);
        } else if (packet instanceof CancelGameRequest) {
            container = new Container("cancelGameRequest", packet);
        } else if (packet instanceof CreateGameRequest) {
            container = new Container("createGameRequest", packet);
        } else if (packet instanceof DoThrowRequest) {
            container = new Container("doThrowRequest", packet);
        } else if (packet instanceof JoinGameRequest) {
            container = new Container("joinGameRequest", packet);
        } else if (packet instanceof LeaveGameRequest) {
            container = new Container("leaveGameRequest", packet);
        } else if (packet instanceof StartGameRequest) {
            container = new Container("startGameRequest", packet);
        } else if (packet instanceof UndoThrowRequest) {
            container = new Container("undoThrowRequest", packet);
        } else if (packet instanceof AuthResponse) {
            container = new Container("authResponse", packet);
        } else if (packet instanceof CancelGameResponse) {
            container = new Container("cancelGameResponse", packet);
        } else if (packet instanceof CreateGameResponse) {
            container = new Container("createGameResponse", packet);
        } else if (packet instanceof DoThrowResponse) {
            container = new Container("doThrowResponse", packet);
        } else if (packet instanceof JoinGameResponse) {
            container = new Container("joinGameResponse", packet);
        } else if (packet instanceof LeaveGameResponse) {
            container = new Container("leaveGameResponse", packet);
        } else if (packet instanceof StartGameResponse) {
            container = new Container("startGameResponse", packet);
        } else if (packet instanceof UndoThrowResponse) {
            container = new Container("undoThrowResponse", packet);
        } else {
            container = null;
        }

        if(container == null) {
            throw new Exception("Can't encode payload: " + packet.getClass());
        }

        return gson.toJson(container, Container.class);
    }

}
