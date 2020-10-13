package dartServer.networking.handlers;

import dartServer.Server;
import dartServer.networking.User;
import dartServer.networking.api.API;
import dartServer.networking.artefacts.Container;
import dartServer.networking.artefacts.ContainerDecoder;
import dartServer.networking.artefacts.ContainerEncoder;
import dartServer.networking.artefacts.requests.AuthRequest;
import dartServer.networking.artefacts.responses.AuthResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public class AuthenticationHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        Container container = ContainerDecoder.decode((String) msg);

        if (container != null) {
            AuthRequest authRequest = (AuthRequest) container.payload;
            if (authRequest != null) {
                AuthResponse authResponse = API.authenticate(authRequest);

                if (authResponse.successful) {
                    Server.instance.createUser(authRequest.username, ctx.channel());
                    ctx.pipeline().replace(this, "containerHandler", new ContainerHandler());
                }
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(authResponse)));
            }
        }
    }
}
