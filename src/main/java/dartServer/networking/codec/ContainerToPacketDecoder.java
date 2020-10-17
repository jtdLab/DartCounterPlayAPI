package dartServer.networking.codec;

import dartServer.commons.packets.Packet;
import dartServer.commons.packets.PacketContainer;
import dartServer.networking.exceptions.ProtocolViolationException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Decoding a {@link PacketContainer} to {@link Packet}
 */
public class ContainerToPacketDecoder extends MessageToMessageDecoder<PacketContainer> {

    static final Logger logger = LogManager.getLogger(ContainerToPacketDecoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, PacketContainer container, List<Object> out) throws Exception {
        logger.trace("Decoding: " + container);
        Packet p = container.getPayload();
        if (p == null)
            throw new ProtocolViolationException();
        out.add(p);
        logger.trace("Decoded: " + p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
