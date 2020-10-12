package dartServer.networking.handlers;

import dartServer.Server;
import dartServer.networking.User;
import dartServer.networking.artefacts.Container;
import dartServer.networking.artefacts.ContainerDecoder;
import dartServer.networking.artefacts.ContainerEncoder;
import dartServer.networking.artefacts.responses.CreateGameResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ContainerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Container container = ContainerDecoder.decode((String) msg);

        switch (container.type) {
            case "cancelGameRequest":

                break;
            case "createGameRequest":
                User user = Server.instance.getUser(ctx.channel());
                //Server.instance.
                //ctx.channel().write(new TextWebSocketFrame(ContainerEncoder.encode(new CreateGameResponse())));
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
