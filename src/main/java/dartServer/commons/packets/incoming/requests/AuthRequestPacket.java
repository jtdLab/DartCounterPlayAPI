package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;
import dartServer.commons.parsing.validators.Username;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Packet for a client to request authentication.
 */
public class AuthRequestPacket implements RequestPacket {

    @NotBlank
    private final String uid;

    @Username
    private final String username;

    public AuthRequestPacket(String uid, String username) {
        this.uid = uid;
        this.username = username;

    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "AuthRequestPacket{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthRequestPacket that = (AuthRequestPacket) o;
        return Objects.equals(uid, that.uid) &&
                Objects.equals(username, that.username);
    }

}
