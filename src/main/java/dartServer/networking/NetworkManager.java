package dartServer.networking;

import dartServer.networking.events.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The NetworkManager is used to register event listeners and firing events
 */
public class NetworkManager {

    static final Logger logger = LogManager.getLogger(NetworkManager.class);

    /**
     * HashMap containing a {@link NetworkEvent} name together with a list of event handlers to call
     */
    private static HashMap<String, List<EventHandler>> listenerMethods = new HashMap<>();

    /**
     * HashMap containing a {@link dartServer.commons.packets.Packet} name together with a list of event handlers to call
     */
    private static HashMap<String, List<EventHandler>> packetListenerMethods = new HashMap<>();

    /**
     * Fire all event listeners for a specific {@link NetworkEvent}
     *
     * @param event
     */
    public static void fireEvent(NetworkEvent event) {
        try {
            if (listenerMethods.containsKey(event.getClass().getName())) {
                List<EventHandler> handlers = listenerMethods.get(event.getClass().getName());
                Collections.sort(handlers);
                for (EventHandler h : handlers)
                    h.fire(event);
            } else if (event instanceof PacketReceiveEvent) {
                if (packetListenerMethods.containsKey(((PacketReceiveEvent) event).getType().getPacketClass().getName())) {
                    List<EventHandler> handlers = packetListenerMethods.get(((PacketReceiveEvent) event).getType().getPacketClass().getName());
                    Collections.sort(handlers);
                    for (EventHandler h : handlers)
                        h.fire(event);
                }
            }
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    /**
     * Register a {@link NetworkEventListener} class to be called on events
     * This method checks the class for methods annotated with @{@link Event} and then adds them to a list
     *
     * @param listener The NetworkEventListener class to add
     */
    public static void registerListener(NetworkEventListener listener) {
        Method[] allMethods = listener.getClass().getMethods();

        for (Method eventMethod : allMethods) {
            if (eventMethod.isAnnotationPresent(Event.class)) {
                if (NetworkEvent.class.isAssignableFrom(eventMethod.getParameterTypes()[0])) {
                    // The first parameter is of the NetworkEvent class type (or extends it)
                    if (PacketReceiveEvent.class.isAssignableFrom(eventMethod.getParameterTypes()[0])) {
                        Type[] genericParameterTypes = eventMethod.getGenericParameterTypes();
                        for (int i = 0; i < genericParameterTypes.length; i++) {
                            if (genericParameterTypes[i] instanceof ParameterizedType) {
                                Type[] parameters = ((ParameterizedType) genericParameterTypes[i]).getActualTypeArguments();
                                //parameters[0] contains java.lang.String for method like "method(List<String> value)"

                                EventHandler h = new EventHandler(listener, eventMethod, eventMethod.getAnnotation(Event.class));

                                logger.trace("Register packet event handler: " + eventMethod.getName());
                                if (!packetListenerMethods.containsKey(parameters[0].getTypeName())) {
                                    List<EventHandler> handlers = new LinkedList<>();
                                    handlers.add(h);
                                    packetListenerMethods.put(parameters[0].getTypeName(), handlers);
                                } else {
                                    packetListenerMethods.get(parameters[0].getTypeName()).add(h);
                                }
                            }
                        }
                    } else {
                        EventHandler h = new EventHandler(listener, eventMethod, eventMethod.getAnnotation(Event.class));

                        logger.trace("Register event handler: " + eventMethod.getName());
                        if (!listenerMethods.containsKey(eventMethod.getParameterTypes()[0].getName())) {
                            List<EventHandler> handlers = new LinkedList<>();
                            handlers.add(h);
                            listenerMethods.put(eventMethod.getParameterTypes()[0].getName(), handlers);
                        } else {
                            listenerMethods.get(eventMethod.getParameterTypes()[0].getName()).add(h);
                        }
                    }
                }
            }
        }
    }
}
