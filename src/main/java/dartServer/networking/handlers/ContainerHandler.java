package dartServer.networking.handlers;

import dartServer.Server;
import dartServer.model.Game;
import dartServer.networking.User;
import dartServer.networking.artefacts.Container;
import dartServer.networking.artefacts.ContainerDecoder;
import dartServer.networking.artefacts.ContainerEncoder;
import dartServer.networking.artefacts.requests.JoinGameRequest;
import dartServer.networking.artefacts.requests.StartGameRequest;
import dartServer.networking.artefacts.responses.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ContainerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Container container = ContainerDecoder.decode((String) msg);
        User user;
        Game game;
        boolean successful;

        switch (container.type) {
            case "cancelGameRequest":
                user = Server.instance.getUser(ctx.channel());
                successful = Server.instance.deleteLobby(user);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new CancelGameResponse(successful))));
                // TODO
                break;
            case "createGameRequest":
                user = Server.instance.getUser(ctx.channel());
                Server.instance.createLobby(user);
                game = Server.instance.getGame(user);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new CreateGameResponse(game.getSnapshot()))));
                break;
            case "doThrowRequest":
                // TODO
                break;
            case "joinGameRequest":
                JoinGameRequest joinGameRequest = (JoinGameRequest) container.payload;
                user = Server.instance.getUser(ctx.channel());
                successful = Server.instance.joinLobby(user, joinGameRequest.username);
                if(successful) {
                    game = Server.instance.getGame(user);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new JoinGameResponse(true, game.getSnapshot()))));
                } else {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new JoinGameResponse(false, null))));
                }
                break;
            case "leaveGameRequest":
                user = Server.instance.getUser(ctx.channel());
                successful = Server.instance.leaveLobby(user);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new LeaveGameResponse(successful))));
                break;
            case "startGameRequest":
                user = Server.instance.getUser(ctx.channel());
                successful = Server.instance.startGame(user);
                if(successful) {
                    game = Server.instance.getGame(user);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new StartGameResponse(true, game.getSnapshot()))));
                } else {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new StartGameResponse(false, null))));
                }
                break;
            case "undoThrowRequest":
                // TODO
                break;
        }
    }

}
