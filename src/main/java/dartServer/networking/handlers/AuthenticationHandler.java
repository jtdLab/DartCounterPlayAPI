package dartServer.networking.handlers;

import dartServer.Server;
import dartServer.networking.api.API;
import dartServer.networking.artefacts.Container;
import dartServer.networking.artefacts.Packet;
import dartServer.networking.artefacts.requests.AuthRequest;
import dartServer.networking.artefacts.responses.AuthResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public class AuthenticationHandler extends SimpleChannelInboundHandler<Container> {

    private Server server;

    public AuthenticationHandler(Server server) {
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Container container) {
        Packet packet = container.packet;

        if(packet instanceof AuthRequest) {
            AuthRequest authRequest = (AuthRequest) packet;
            AuthResponse authResponse = API.authenticate(authRequest);

            if (authResponse.successful) {
                createUser(authRequest.username, channelHandlerContext.channel());
                channelHandlerContext.pipeline().replace(this, "sessionHandler", new SessionHandler(server));
            }
            channelHandlerContext.channel().writeAndFlush(authResponse);
        }
    }

    private void createUser(String username, Channel channel) {
        server.createUser(username, channel);
    }
}
