package dartServer.commons.packets.incoming.requests;

import dartServer.commons.packets.incoming.RequestPacket;
import dartServer.commons.validators.Username;

import javax.validation.constraints.NotBlank;

/**
 * Packet for a client to request authentication.
 */
public class AuthRequestPacket implements RequestPacket {

    @Username
    private String username;
    @NotBlank
    private String password;

    public AuthRequestPacket(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return "AuthRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                "}";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
