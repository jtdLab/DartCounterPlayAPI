package dartServer.networking.artefacts;

import java.sql.Timestamp;

public class Container implements Payload {

    public String type;
    public Payload payload;
    public Timestamp timestamp;

    public Container(String type, Payload payload) {
        this.type = type;
        this.payload = payload;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Container {" + type + "}";
    }
}
