package dartServer.commons.packets;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Objects;

/**
 * This class represents a "container" for network packets from the standardization document.
 */
public class PacketContainer {

    /**
     * The timestamp the packet was sent
     */
    @NotNull
    @PastOrPresent
    private Date timestamp;

    /**
     * The payloadType, meaning which type of packet the container contains
     */
    @NotNull
    @Valid
    private PacketType payloadType;

    /**
     * The payload of the container, e.g. the network packet
     */
    @NotNull
    @Valid
    private Packet payload;

    public PacketContainer(Packet payload) {
        this.timestamp = new Date();
        if (payload != null) {
            this.payloadType = PacketType.forClass(payload.getClass());
            this.payload = payload;
        }
    }

    public PacketType getPayloadType() {
        return payloadType;
    }

    public Packet getPayload() {
        return payload;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null)
            throw new IllegalArgumentException("Timestamp may not be null");
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PacketContainer{" +
                "timestamp=" + timestamp +
                ", payloadType=" + payloadType +
                ", payload=" + payload +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketContainer container = (PacketContainer) o;
        return Objects.equals(timestamp, container.timestamp) &&
                payloadType == container.payloadType &&
                Objects.equals(payload, container.payload);
    }

}
