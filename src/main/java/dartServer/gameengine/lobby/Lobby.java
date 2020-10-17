package dartServer.gameengine.lobby;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.Game;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.GameLoop;
import dartServer.gameengine.model.Throw;

import java.util.Arrays;

/**
 * PacketContainer for participants and spectators of an upcoming match
 * Users are listed after logging into the Server
 */
public class Lobby {

    private static long id;

    private long lobbyId;
    Game activeGame;

    private GameLoop gameLoop;

    private boolean matchCreationStarted;

    // --== Constructor ==--
    public Lobby(User user) {
        this.matchCreationStarted = false;
        activeGame = new Game(user);
        lobbyId = id++;
    }

    // --== GameLoop ==--

    public void startGame() {

    }


    /**
     * Part of the gameloop in the lobby. Broadcasts the next required action to all Users in the Lobby
     */
    private void broadcastNextAction() {
        Thread newAction = new Thread() {
            public void run() {


            }
        };

        newAction.start();
    }

    /**
     * Gets called every time a valid throw is received
     * @param t
     */
    public void throwPerformed(Throw t) {
        activeGame.performThrow(t);
    }

    public void undoThrow() {
        activeGame.undoThrow();
    }

    public void addUser(User user) {
        activeGame.addUser(user);
    }

    public void removeUser(User user){
        activeGame.removeUser(user);
    }

    public void start(){
        activeGame.start();
    }

    // --== Methods ==--

    /**
     * broadcasts the given packet to all Clients in the Lobby.
     *
     * @param packet the packet that needs to be sent
     */
    public void broadcastToUsers(ResponsePacket packet) {
        for (User u : activeGame.getUsers()) {
            if (u.isConnected())
                u.sendMessage(packet);
        }
    }

    // --== Getter/Setter ==--

    public Game getActiveGame() {
        return activeGame;
    }

    public boolean isMatchCreationStarted() {
        return matchCreationStarted;
    }

    public void setMatchCreationStarted(boolean matchCreationStarted) {
        this.matchCreationStarted = matchCreationStarted;
    }

    public User[] getUsers() {
        return Arrays.stream(GameEngine.getUsers()).filter(user -> user.getLobbyId() == this.id).toArray(User[]::new);
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public long getLobbyId() {
        return lobbyId;
    }
}
