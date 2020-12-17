package dartServer.networking.codec;

import dartServer.commons.packets.PacketContainer;
import dartServer.commons.parsing.validators.JsonValidator;
import dartServer.networking.exceptions.ProtocolViolationException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class IncomingPacketContainerValidator extends MessageToMessageDecoder<PacketContainer> {

    static final Logger logger = LoggerFactory.getLogger(IncomingPacketContainerValidator.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, PacketContainer container, List<Object> out) throws Exception {
        if (!JsonValidator.isPacketContainerValid(container)) {
            logger.warn("Received invalid packet container! Discarding whole packet.");
            throw new ProtocolViolationException();
        }
        out.add(container);
    }

}
