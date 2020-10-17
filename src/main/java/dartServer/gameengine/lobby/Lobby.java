package dartServer.gameengine.lobby;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.Game;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.GameLoop;

import java.util.Arrays;
import java.util.Calendar;

/**
 * PacketContainer for participants and spectators of an upcoming match
 * Users are listed after logging into the Server
 */
public class Lobby {

    Game activeGame;
    private String name;

    private GameLoop gameLoop;

    private boolean matchCreationStarted;

    // --== Constructor ==--

    /**
     * instantiates a lobby with given name and user list
     *
     * @param name name of the lobby
     */
    public Lobby(String name) {
        this.name = name;
        this.matchCreationStarted = false;

        activeGame = new Game();
    }


    // --== GameLoop ==--

    /**
     * Starts the GameLoop and setUps BallPositions
     */


    public void startMatch() {

    }




    /**
     * Part of the gameloop in the lobby. Broadcasts the next required action to all Users in the Lobby
     */
    private void broadcastNextAction() {
        Thread newAction = new Thread() {
            public void run() {

                int timeout;
                if (activeGame.getPhase() == PhaseType.BALLPHASE)
                    timeout = GameEngine.getMatchConfig().getTimeouts().getPlayerTurnTimeout();
                else
                    timeout = GameEngine.getMatchConfig().getTimeouts().getFanTurnTimeout();

                ResponsePacket action = gameLoop.getNextAction(timeout);
                if (!(action instanceof MatchFinishPacket)) {
                    broadcastToUsers(action);    //Broadcast next Action or deltaPacket in ballPhase
                } else {//If gameIsOver
                    broadcastToUsers(action);
                    return;
                }

                //Timeout feststellen
                long timerEnd = Calendar.getInstance().getTimeInMillis() + timeout;
                long restTimer = 0;

                if (!gameLoop.isWaitingForResponse()) {
                    broadcastNextAction();
                    return;
                }

                while (gameLoop.isWaitingForResponse()) {
                    if (gameLoop.isGameOver()) {
                        broadcastNextAction();
                        return;
                    } else if (gameLoop.isPaused()) {
                        if (restTimer == 0)
                            restTimer = timerEnd - Calendar.getInstance().getTimeInMillis();
                    } else if (gameLoop.isContinued()) {
                        timerEnd = Calendar.getInstance().getTimeInMillis() + restTimer;
                        restTimer = 0;
                        gameLoop.setContinued(false);
                    } else if (timerEnd <= Calendar.getInstance().getTimeInMillis()) {
                        broadcastToUsers(new GlobalDebugPacket("Timeout of " + timeout + " milliseconds exceeded. Next Action requested!"));
                        broadcastNextAction();
                        return;
                    }
                }
            }
        };

        newAction.start();
    }

    /**
     * Gets called every time a valid DealtaRequest is received
     *
     * @param packet The DeltaRequest packet
     */
    public void actionPerformed(DeltaRequestPacket packet) {

    }

    // --== Methods ==--

    /**
     * broadcasts the given packet to all Clients in the Lobby.
     *
     * @param packet the packet that needs to be sent
     */
    public void broadcastToUsers(ResponsePacket packet) {
        if (activeGame.getLeftUser() != null && activeGame.getLeftUser().isConnected())
            activeGame.getLeftUser().sendMessage(packet);
        if (activeGame.getRightUser() != null && activeGame.getRightUser().isConnected())
            activeGame.getRightUser().sendMessage(packet);
        for (User u : activeGame.getSpectators()) {
            if (u.isConnected())
                u.sendMessage(packet);
        }
    }

    public ReconnectPacket getReconnectPacket() {
        return new ReconnectPacket(this.getActiveGame().getMatchStart(), this.getActiveGame().getSnapshotPacket(), this.getGameLoop().getCurrentNext());
    }

    // --== Getter/Setter ==--

    public Game getActiveGame() {
        return activeGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMatchCreationStarted() {
        return matchCreationStarted;
    }

    public void setMatchCreationStarted(boolean matchCreationStarted) {
        this.matchCreationStarted = matchCreationStarted;
    }

    public User[] getUsers() {
        return Arrays.stream(GameEngine.getUsers()).filter(user -> user.getLobbyName().equals(this.name)).toArray(User[]::new);
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

}
