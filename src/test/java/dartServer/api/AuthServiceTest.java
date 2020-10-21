package dartServer.api;

import dartServer.commons.packets.incoming.requests.AuthRequestPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceTest {

    /**
     * Needs AuthService to be available and mrjosch to exist (with matching password)
     */
    @Test
    public void testAuthenticate() {
        assertTrue(AuthService.authenticate(new AuthRequestPacket("mrjosch", "sanoj050499")));
        assertFalse(AuthService.authenticate(new AuthRequestPacket("notregistered", "afafafaf")));
    }

}