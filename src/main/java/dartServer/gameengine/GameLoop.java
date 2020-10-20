package dartServer.gameengine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Evaluates the game-state of FantasticFeastsGame Class and coordinates phases and actions during match
 */
public class GameLoop {

    static final Logger logger = LogManager.getLogger(GameLoop.class);
    private final Game game;

    private boolean paused;
    private boolean continued;
    private boolean waitingForResponse;


    public GameLoop(Game game) {
        this.game = game;
        paused = false;
        continued = false;
    }

    /**
     * Retrieves whether the GameLoop requested a response on a Next-Packet
     *
     * @return true if response needed -> timeout should start, otherwise false -> continue immediately
     */
    public boolean isWaitingForResponse() {
        return waitingForResponse;
    }

    /**
     * Informs the gameLoop that requested response was received.
     */
    public void setReply() {
        waitingForResponse = false;
    }

    public void exitPause() {
        continued = true;
        paused = false;
    }

    public void enterPause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isContinued() {
        return continued;
    }

    public void setContinued(boolean continued) {
        this.continued = continued;
    }

}
