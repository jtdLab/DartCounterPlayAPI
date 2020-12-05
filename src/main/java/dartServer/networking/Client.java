package dartServer.networking;

import dartServer.commons.packets.Packet;
import dartServer.commons.packets.PacketType;
import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.commons.parsing.validators.JsonValidator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.SocketAddress;

public class Client {

    static final Logger logger = LogManager.getLogger(Client.class);

    private static long id;

    private final long clientId;
    private final Channel channel;
    private boolean kicked;

    public Client(Channel channel) {
        this.clientId = id;
        this.channel = channel;
        id++;
        kicked = false;
    }

    public SocketAddress getAddress() {
        return channel.remoteAddress();
    }

    public void disconnect() {
        if (channel.isOpen()) {
            channel.disconnect();
            channel.close();
        }
    }

    public void sendPacket(ResponsePacket packet) {
        sendPackets(packet);
    }

    public void sendPackets(ResponsePacket... packets) {
        if (channel.isOpen() && channel.isActive() && channel.isWritable()) {
            for (Packet p : packets) {
                if (!JsonValidator.isPacketValid(p)) {
                    logger.fatal("Refusing to send invalid packet to client!");
                    continue;
                }
                channel.writeAndFlush(p).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE); // get errors
                logger.debug("Sent " + PacketType.forClass(p.getClass()) + " packet to " + this);
            }
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + clientId +
                ", adr=" + getAddress() +
                '}';
    }

    public boolean isKicked() {
        return kicked;
    }

    public void setKicked(boolean wasKicked) {
        this.kicked = wasKicked;
    }
}
