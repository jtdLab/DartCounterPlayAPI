package dartServer.networking.codec;

import com.google.gson.Gson;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.parsing.JsonManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Encoding a {@link PacketContainer} to JSON
 */
public class ContainerToJsonEncoder extends MessageToMessageEncoder<PacketContainer> {

    static final Logger logger = LogManager.getLogger(ContainerToJsonEncoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, PacketContainer container, List out) throws Exception {
        logger.trace("Encoding: " + container);
        Gson gson = JsonManager.getGson();
        String json = gson.toJson(container);
        out.add(new TextWebSocketFrame(json));
        logger.trace("Encoded: " + json);
    }
}
