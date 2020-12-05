package dartServer.networking.codec;

import dartServer.commons.parsing.JsonManager;
import dartServer.commons.packets.PacketContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Decoding JSON to {@link PacketContainer}
 */
public class JsonToContainerDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {

    static final Logger logger = LogManager.getLogger(JsonToContainerDecoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame, List<Object> out) throws Exception {
        String msg = textWebSocketFrame.text();
        logger.trace("Decoding: " + msg.replace("\n", "").replace("\r", ""));

        PacketContainer c = JsonManager.getGson().fromJson(msg, PacketContainer.class);

        // Overwrite timestamp - because users can send wrong timestamps
        c.setTimestamp(new Date());

        out.add(c);
        logger.trace("Decoded: " + c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
