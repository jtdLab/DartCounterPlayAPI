package dartServer.networking.codec;

import dartServer.commons.packets.Packet;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.packets.PacketType;
import dartServer.networking.exceptions.ProtocolViolationException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Encoding a {@link Packet} to {@link PacketContainer}
 */
public class PacketToContainerEncoder extends MessageToMessageEncoder<Packet> {

    static final Logger logger = LoggerFactory.getLogger(PacketToContainerEncoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet p, List out) throws Exception {
        logger.trace("Encoding: " + p);

        PacketType packetType = PacketType.forClass(p.getClass());

        if (packetType == null) {
            throw new ProtocolViolationException();
        }

        PacketContainer c = new PacketContainer(p);
        out.add(c);
        logger.trace("Encoded: " + c);
    }

}
