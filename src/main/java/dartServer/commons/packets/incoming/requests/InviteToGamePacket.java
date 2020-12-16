package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Packet for client to join a game.
 */
public class InviteToGamePacket implements RequestPacket {

    @NotNull
    private final String uid;

    @NotNull
    private final String username;

    public InviteToGamePacket(String uid, String username) {
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
        return "InviteToGamePacket{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InviteToGamePacket invite = (InviteToGamePacket) o;
        return Objects.equals(uid, invite.uid) && Objects.equals(username, invite.username);
    }

}
