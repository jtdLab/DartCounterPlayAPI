package dartServer.networking.handler;

import dartServer.commons.packets.Packet;
import dartServer.commons.packets.PacketType;
import dartServer.commons.packets.incoming.RequestPacket;
import dartServer.networking.Client;
import dartServer.networking.NetworkManager;
import dartServer.networking.events.ClientConnectEvent;
import dartServer.networking.events.ClientDisconnectEvent;
import dartServer.networking.events.ClientExceptionEvent;
import dartServer.networking.events.PacketReceiveEvent;
import dartServer.networking.exceptions.ClientException;
import dartServer.networking.exceptions.InvalidJsonException;
import dartServer.networking.exceptions.ProtocolViolationException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * This handler reads incoming packets and fires the specific events
 */
public class WebSocketServerHandler extends ChannelInboundHandlerAdapter {

    static final Logger logger = LogManager.getLogger(WebSocketServerHandler.class);

    private Client client;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Packet)) {
            logger.warn("Invalid object reached the server handler (only packets allowed here). That should not happen!");
            logger.trace(msg.getClass());
            return;
        }

        Packet p = (Packet) msg;

        if (!(p instanceof RequestPacket)) {
            logger.warn(client + " sent invalid packet, that is not of the RequestPacket type.");
            logger.info("This usually means that the client tried to sent a server response packet to the server.");
            logger.debug("Dropping packet: " + p);
            throw new ProtocolViolationException();
        }

        RequestPacket rp = (RequestPacket) p;

        logger.info("Received " + PacketType.forClass(rp.getClass()) + " from " + client);
        logger.trace(client + " sent " + msg);

        PacketReceiveEvent event = new PacketReceiveEvent(client, rp);
        NetworkManager.fireEvent(event);

        if (event.isRejected()) {
            event.getClient().setKicked(true);
            event.getClient().disconnect();
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        client = new Client(ctx.channel());
        NetworkManager.fireEvent(new ClientConnectEvent(client));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        NetworkManager.fireEvent(new ClientDisconnectEvent(client));
        client = null;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof CorruptedFrameException) {
            logger.warn(ctx.channel().remoteAddress() + " sent invalid JSON and gets disconnected!");
            exceptionCaught(ctx, new InvalidJsonException());
        } else if (cause instanceof ClientException) {
            NetworkManager.fireEvent(new ClientExceptionEvent(client, ((ClientException) cause).getReason()));
            client.disconnect();
        } else if (cause instanceof DecoderException) {
            exceptionCaught(ctx, cause.getCause());
        } else if (cause instanceof IOException) {
            logger.debug("IOException (mostly happens when client closes the connection)");
        } else {
            logger.warn("Unkonwn exception", cause);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        client = new Client(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        String s = (String) evt;
        if (client != null && s.equals("upgraded")) {
            NetworkManager.fireEvent(new ClientConnectEvent(client));
        }
       /* if (client != null && evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            NetworkManager.fireEvent(new ClientConnectEvent(client));
        }*/
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        NetworkManager.fireEvent(new ClientDisconnectEvent(client));
        client = null;
    }
}