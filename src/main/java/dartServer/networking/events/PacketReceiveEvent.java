package dartServer.networking.events;


import dartServer.commons.packets.PacketType;
import dartServer.commons.packets.incoming.RequestPacket;
import dartServer.networking.Client;

public class PacketReceiveEvent<T extends RequestPacket> extends NetworkEvent {

    private final T packet;
    private final PacketType type;

    /**
     * Whether the event is rejected, meaning the client gets kicked
     */
    private boolean isRejected;

    /**
     * Debug message to be sent to the client if the event is rejected
     */
    private String message;

    public PacketReceiveEvent(Client client) {
        this(client, null);
    }

    public PacketReceiveEvent(Client client, T packet) {
        super(client);
        this.packet = packet;
        this.type = PacketType.forClass(packet.getClass());
    }

    public T getPacket() {
        return packet;
    }

    public PacketType getType() {
        return type;
    }

    /**
     * Check if the event is rejected.
     *
     * @return True if the event is rejected, otherwise false
     */
    public boolean isRejected() {
        return isRejected;
    }

    /**
     * Set whether the event is rejected, meaning the client will get kicked after all event listeners are fired
     *
     * @param rejected True if the event should be rejected
     */
    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    /**
     * Set whether the event is rejected, meaning the client will get kicked after all event listeners are fired
     *
     * @param rejected True if the event should be rejected
     * @param message  Optional message that will be sent to the client
     */
    public void setRejected(boolean rejected, String message) {
        this.isRejected = rejected;
        this.message = message;
    }

    /**
     * Get the optional message to be sent to the client after the event is rejected
     *
     * @return Debug Message
     */
    public String getMessage() {
        return message;
    }
}
