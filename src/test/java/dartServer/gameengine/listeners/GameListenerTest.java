package dartServer.gameengine.listeners;

import dartServer.commons.packets.incoming.requests.*;
import dartServer.networking.events.PacketReceiveEvent;
import org.junit.jupiter.api.Test;

class GameListenerTest {

    @Test
    void testOnStartGame() {
        PacketReceiveEvent<StartGamePacket> event = new PacketReceiveEvent<>(null);
        GameListener gameListener = new GameListener();
        // TODO
    }

    @Test
    void testOnCancelGame() {
        PacketReceiveEvent<CancelGamePacket> event = new PacketReceiveEvent<>(null);
        GameListener gameListener = new GameListener();
        // TODO
    }

    @Test
    void testOnExitGame() {
        PacketReceiveEvent<ExitGamePacket> event = new PacketReceiveEvent<>(null);
        GameListener gameListener = new GameListener();
        // TODO
    }

    @Test
    void testOnPerformThrow() {
        PacketReceiveEvent<PerformThrowPacket> event = new PacketReceiveEvent<>(null);
        GameListener gameListener = new GameListener();
        // TODO
    }

    @Test
    void testOnUndoThrow() {
        PacketReceiveEvent<UndoThrowPacket> event = new PacketReceiveEvent<>(null);
        GameListener gameListener = new GameListener();
        // TODO
    }
}