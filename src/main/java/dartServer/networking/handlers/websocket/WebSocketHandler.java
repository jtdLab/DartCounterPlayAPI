package dartServer.networking.handlers.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * server.networking.handlers.websocket.WebSocketHandler converts incoming TextWebSocketFrame to String and forwards it
 */

public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof TextWebSocketFrame) {
            System.out.println("Received: " + ((TextWebSocketFrame) msg).text());
            ctx.fireChannelRead(((TextWebSocketFrame) msg).text());
        } else {
            // TODO unknown data received add log
        }
    }

}