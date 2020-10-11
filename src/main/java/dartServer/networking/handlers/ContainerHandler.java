package dartServer.networking.handlers;

import dartServer.Server;
import dartServer.networking.User;
import dartServer.networking.artefacts.Container;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ContainerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Container container = (Container) msg;

        switch (container.type) {
            case "cancelGameRequest":

                break;
            case "createGameRequest":
                User user = Server.instance.getUser(ctx.channel());
                Server.instance.getGameManager().createGame(user);
                break;
            case "doThrowRequest":

                break;
            case "joinGameRequest":

                break;
            case "startGameRequest":

                break;
            case "undoThrowRequest":

                break;
        }
    }

}
