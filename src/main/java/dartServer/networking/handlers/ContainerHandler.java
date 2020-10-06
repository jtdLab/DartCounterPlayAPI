package dartServer.networking.handlers;

import dartServer.networking.artefacts.Container;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ContainerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Container container = (Container) msg;

        switch (container.type) {
            case "createGameRequest":

                break;
            case "joinGameRequest":

                break;
            case "startGameRequest":

                break;
            case "doThrowRequest":

                break;
            case "undoThrowRequest":

                break;
            case "cancelGameRequest":

                break;
        }
    }

}
