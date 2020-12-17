package dartServer.networking.events;

import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An event handler is used to fire an event method in an event listener class.
 */
public class EventHandler implements Comparable<EventHandler> {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);

    private final NetworkEventListener listener;
    private final Method eventMethod;
    private final Event annotation;

    /**
     * Create a new event handler
     *
     * @param listener    The event listener class
     * @param eventMethod The method to call in the specific event listener class
     * @param annotation  The annotation of the specific method
     */
    public EventHandler(NetworkEventListener listener, Method eventMethod, Event annotation) {
        this.listener = listener;
        this.eventMethod = eventMethod;
        this.annotation = annotation;
    }

    /**
     * Fire the event handler for the specific {@link NetworkEvent}
     *
     * @param event
     */
    public void fire(NetworkEvent event) {
        try {
            eventMethod.invoke(listener, event);
            printServerState();
        } catch (InvocationTargetException e) {
            logger.warn("Error in event listener!", e.getTargetException());
        } catch (Exception e) {
            logger.warn("Error in event listener!", e);
        }
    }

    /**
     * Compare two event handlers against their priorities.
     *
     * @param other The other event handler
     * @return An integer value for sorting the event handlers by priority
     */
    @Override
    public int compareTo(EventHandler other) {
        return other.annotation.priority().getValue() - this.annotation.priority().getValue();
    }

    @Override
    public String toString() {
        return "EventHandler{" +
                "listener=" + listener +
                ", eventMethod=" + eventMethod +
                ", annotation=" + annotation +
                '}';
    }


    private void printServerState() {
        /**
         * logger.warn("");
         *         logger.warn("======================================");
         *         logger.warn("Players: " + GameEngine.getUsers().length + " (online)");
         *         for (User user : GameEngine.getUsers()) {
         *             logger.warn(user);
         *         }
         *         logger.warn("");
         *         logger.warn("Lobbies: " + GameEngine.getLobbies().size() + " (active)");
         *         for (Lobby lobby : GameEngine.getLobbies()) {
         *             logger.warn(lobby);
         *         }
         *         logger.warn("======================================");
         *         logger.warn("");
         */

        System.out.println();
        System.out.println("======================================");
        System.out.println("Players: " + GameEngine.getUsers().length + " (online)");
        for (User user : GameEngine.getUsers()) {
            System.out.println(user);
        }
        System.out.println();
        System.out.println("Lobbies: " + GameEngine.getLobbies().size() + " (active)");
        for (Lobby lobby : GameEngine.getLobbies()) {
            System.out.println(lobby);
        }
        System.out.println("======================================");
        System.out.println();
    }
}
