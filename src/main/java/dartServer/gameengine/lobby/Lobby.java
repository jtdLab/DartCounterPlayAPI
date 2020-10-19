package dartServer.gameengine.lobby;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.Game;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.model.Player;
import dartServer.gameengine.model.Throw;

import java.util.Arrays;

/**
 * PacketContainer for participants and spectators of an upcoming match
 * Users are listed after logging into the Server
 */
public class Lobby {

    private static long id = 1;

    private long lobbyId;
    Game activeGame;

    //private GameLoop gameLoop;

    private boolean matchCreationStarted;

    // --== Constructor ==--
    public Lobby(User user) {
        this.matchCreationStarted = false;
        activeGame = new Game(user);
        user.setPlayer(new Player(user.getName()));
        lobbyId = id++;
    }

    // --== GameLoop ==--

    public void startGame() {
        activeGame.start();
    }

    public boolean performThrow(Throw t) {
        return activeGame.performThrow(t);
    }

    public void undoThrow() {
        activeGame.undoThrow();
    }

    public boolean addUser(User user) {
        user.setLobbyId(lobbyId);
        user.setPlayer(new Player(user.getName()));
       if(activeGame.addUser(user)) {
           return true;
       }
       return false;
    }

    public void removeUser(User user){
        activeGame.removeUser(user);
    }

    public boolean start(){
        return activeGame.start();
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

    public long getLobbyId() {
        return lobbyId;
    }

    public String getOwnerName() {
        return activeGame.getUsers().get(0).getName();
    }

    public int getCode() {
        return (int)lobbyId+999;
    }
}
