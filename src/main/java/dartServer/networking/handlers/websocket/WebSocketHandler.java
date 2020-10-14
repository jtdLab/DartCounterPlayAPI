package dartServer.networking.handlers.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * server.networking.handlers.websocket.WebSocketHandler converts incoming TextWebSocketFrame to String and forwards it
 */


public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        // TODO log message received
        String text = textWebSocketFrame.text();
        channelHandlerContext.fireChannelRead(text);
    }

}

