package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.*;
import dartServer.networking.events.Event;
import dartServer.networking.events.NetworkEventListener;
import dartServer.networking.events.PacketReceiveEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listener class for game events.
 */
public class GameListener implements NetworkEventListener {

    static final Logger logger = LogManager.getLogger(GameListener.class);

    /**
     * @param event the event fired on startGame by a client
     */
    @Event
    public void onStartGame(PacketReceiveEvent<StartGamePacket> event) {

    }

    /**
     * @param event the event fired on cancelGame by a client
     */
    @Event
    public void onCancelGame(PacketReceiveEvent<CancelGamePacket> event) {

    }

    /**
     * @param event the event fired on exitGame by a client
     */
    @Event
    public void onExitGame(PacketReceiveEvent<ExitGamePacket> event) {

    }

    /**
     * @param event the event fired on performThrow by a client
     */
    @Event
    public void onPerformThrow(PacketReceiveEvent<PerformThrowPacket> event) {

    }

    /**
     * @param event the event fired on undoThrow by a client
     */
    @Event
    public void onUndoThrow(PacketReceiveEvent<UndoThrowPacket> event) {

    }

}