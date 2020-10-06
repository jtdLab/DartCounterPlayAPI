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



// {"type":"authRequest","payload":{"username":"pblanda","password":"$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi"},"timestamp":"Oct 5, 2020, 6:14:45 PM"}
public class AuthenticationHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        Container container = ContainerDecoder.decode((String) msg);

        if(container != null) {
            AuthRequest authRequest = (AuthRequest) container.payload;
            if(authRequest != null) {
                AuthResponse authResponse = API.authenticate(authRequest);

                if(authResponse.successful) {
                   // TODO add user
                    ctx.pipeline().replace(this, "containerHandler", new ContainerHandler());
                }

                ctx.channel().writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(authResponse)));
            }
        }
    }
}
