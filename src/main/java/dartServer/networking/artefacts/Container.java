package dartServer.networking.artefacts;

import java.sql.Timestamp;

public class Container implements Packet {

    public String type;
    public Packet packet;
    public Timestamp timestamp;

    public Container(String type, Packet packet) {
        this.type = type;
        this.packet = packet;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Container {" + type + "}";
    }
}
