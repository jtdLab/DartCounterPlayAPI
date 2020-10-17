package dartServer.gameengine.listeners;

import dartServer.networking.events.AuthEvent;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;

/**
 * Listener class for the event of a new authRequest by a client.
 */
public class AuthenticationListener implements NetworkEventListener {

    /**
     * Debug event.
     *
     * @param event the event fired on authRequest by a client
     */
    @Event
    public void onAuth(AuthEvent event) {

    }

}
