package dartServer.networking.handlers.websocket;

import dartServer.Server;
import dartServer.networking.handlers.AuthenticationHandler;
import dartServer.networking.handlers.codec.ContainerDecoder;
import dartServer.networking.handlers.codec.ContainerEncoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private Server server;

    WebSocketServerHandshaker handshaker;

    public HttpServerHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            HttpHeaders headers = httpRequest.headers();

            if ("Upgrade".equalsIgnoreCase(headers.get(HttpHeaderNames.CONNECTION)) && "WebSocket".equalsIgnoreCase(headers.get(HttpHeaderNames.UPGRADE))) {
                //Adding new handler to the existing pipeline to handle WebSocket Messages
                ctx.pipeline().replace(this, "websocketHandler", new WebSocketHandler());
                ctx.pipeline().addLast("containerDecoder", new ContainerDecoder());
                ctx.pipeline().addLast("containerEncoder", new ContainerEncoder());
                ctx.pipeline().addLast("authHandler", new AuthenticationHandler(server));

                //Do the Handshake to upgrade connection from HTTP to WebSocket protocol
                handleHandshake(ctx, httpRequest);

                System.out.println("New user connected to server");
            }
        } else {
            System.out.println("Incoming request is unknown");
        }
    }

    /* Do the handshaking for WebSocket request */
    protected void handleHandshake(ChannelHandlerContext ctx, HttpRequest req) {
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketURL(req), null, true);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    protected String getWebSocketURL(HttpRequest req) {
        String url = "ws://" + req.headers().get("Host") + req.getUri();
        return url;
    }
}