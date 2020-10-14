package dartServer.networking.handlers;

import dartServer.Server;
import dartServer.model.Game;
import dartServer.networking.User;
import dartServer.networking.artefacts.Container;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class SessionHandler extends ChannelInboundHandlerAdapter {

    private Server server;

    public SessionHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        User user;
        Game game;
        boolean successful;

        System.out.println("CONTAINER");

        /*switch (container.type) {
            case "cancelGameRequest":
                user = server.getUser(ctx.channel());
                successful = server.deleteLobby(user);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new CancelGameResponse(successful))));
                // TODO
                break;
            case "createGameRequest":
                user = server.getUser(ctx.channel());
                server.createLobby(user);
                game = server.getGame(user);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new CreateGameResponse(game.getSnapshot()))));
                break;
            case "doThrowRequest":
                // TODO
                break;
            case "joinGameRequest":
                JoinGameRequest joinGameRequest = (JoinGameRequest) container.payload;
                user = server.getUser(ctx.channel());
                successful = server.joinLobby(user, joinGameRequest.username);
                if(successful) {
                    game = server.getGame(user);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new JoinGameResponse(true, game.getSnapshot()))));
                } else {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new JoinGameResponse(false, null))));
                }
                break;
            case "leaveGameRequest":
                user = server.getUser(ctx.channel());
                successful = server.leaveLobby(user);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new LeaveGameResponse(successful))));
                break;
            case "startGameRequest":
                user = server.getUser(ctx.channel());
                successful = server.startGame(user);
                if(successful) {
                    game = server.getGame(user);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new StartGameResponse(true, game.getSnapshot()))));
                } else {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(new StartGameResponse(false, null))));
                }
                break;
            case "undoThrowRequest":
                // TODO
                break;
        }*/
    }

}
