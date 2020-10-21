package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;
import dartServer.commons.validators.Username;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Packet for a client to request authentication.
 */
public class AuthRequestPacket implements RequestPacket {

    @Username
    private final String username;
    @NotBlank
    private final String password;

    public AuthRequestPacket(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return "AuthRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthRequestPacket that = (AuthRequestPacket) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

}
