package dartServer.gameengine;

import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.gameengine.lobby.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Evaluates the game-state of FantasticFeastsGame Class and coordinates phases and actions during match
 */
public class GameLoop {

    static final Logger logger = LogManager.getLogger(GameLoop.class);
    private Game game;

    private boolean paused;
    private boolean continued;
    private boolean waitingForResponse;
    private NextPacket currentNext;

    private BallPhaseHandler ballPhase;
    private PlayerPhaseHandler playerPhase;
    private FanPhaseHandler fanPhase;
    private GameFinishHandler gameFinish;

    private PhaseType lastPhase;
    private int[] lastScore;


    public GameLoop(Game game) {
        this.game = game;
        paused = false;
        continued = false;

        ballPhase = new BallPhaseHandler(game);
        playerPhase = new PlayerPhaseHandler(game);
        fanPhase = new FanPhaseHandler(game);
        gameFinish = new GameFinishHandler(game);

        lastPhase = PhaseType.BALLPHASE;
        lastScore = new int[]{0, 0};


        startNewRound();
    }

    /**
     * Returns a ResponsePacket with the next action or Snapshot
     *
     * @param timeout the timeout in milliseconds
     * @return the next action if cient's response is required or deltaBoradcast of ball-move
     */
    public ResponsePacket getNextAction(int timeout) {

        //Check whether the protocol was violated --------------
        if (gameFinish.isProtocolViolated()) {
            waitingForResponse = false;
            game.setPhase(PhaseType.GAMEFINISH);
            return gameFinish.getMatchFinishPacket();
        }

        //Return phaseChange and roundChange deltaBroadcast
        if (lastPhase != game.getPhase())
            return getPhaseAndRoundChangeSnapshot();

        //Return goalPointsChangeBroadcast
        if (lastScore[0] != game.getScore()[0] || lastScore[1] != game.getScore()[1])
            return getGoalPointsChangeSnapshot();

        //check whether snitch is caught
        Player seeker = game.getBalls()[0].getPosition().getPlayer();
        if (!gameFinish.isGameOver() && seeker != null && seeker instanceof Seeker) {
            return getSnitchCatchSnapshot();
        }

        //Get next PhaseAction
        return getNextPhaseAction(timeout);

    }


    /**
     * Returns the next action within a game-phase
     *
     * @param timeout the timeout for the reply
     * @return the next game-action
     */
    private ResponsePacket getNextPhaseAction(int timeout) {
        //BallPhase ---------------------------------------------
        if (game.getPhase() == PhaseType.BALLPHASE) {
            waitingForResponse = false;
            return ballPhase.getNextAction();
        }

        //playerPhase ---------------------------------------------
        if (game.getPhase() == PhaseType.PLAYERPHASE) {

            //(temporarily) disqualify team if banned players is >= 3
            gameFinish.checkForTeamDisqualification();

            waitingForResponse = true;
            NextPacket action = playerPhase.getNextAction(timeout);
            if (action != null) {
                currentNext = action;
                return currentNext;
            }

        }

        //fanPhase ---------------------------------------------
        if (game.getPhase() == PhaseType.FANPHASE) {
            waitingForResponse = true;
            NextPacket action = fanPhase.getNextAction(timeout);
            if (action != null) {
                currentNext = action;
                return currentNext;
            } else {
                startNewRound();
                return getNextAction(timeout);
            }
        }

        //gameFinish ---------------------------------------------
        if (game.getPhase() == PhaseType.GAMEFINISH) {
            waitingForResponse = false;
            return gameFinish.getMatchFinishPacket();
        }

        return null;
    }


    /**
     * Gets the SnitchCatch-Snapshot (should be called when snitch is caught)
     *
     * @return SnitchCatch-Snapshot
     */
    private ResponsePacket getSnitchCatchSnapshot() {
        Player seeker = game.getBalls()[0].getPosition().getPlayer();
        boolean success;

        if (gameFinish.snitchCaught()) {
            game.setPhase(PhaseType.GAMEFINISH);

            //Add Score for catching the snitch
            if (gameFinish.snitchCaught()) {
                if (game.getLeftUser().getTeam().getSeeker().grabSnitch())
                    game.addScore("left", 30);
                else
                    game.addScore("right", 30);
            }

            success = true;
        } else {
            success = false;
        }

        EntityIdType entityId = game.getEntityId(seeker);
        game.setLastDeltaBroadcast(new DeltaBroadcast(DeltaType.SNITCH_CATCH, success, game.getScore()[0], game.getScore()[1], entityId));
        return game.getSnapshotPacket();
    }


    /**
     * Gets the GoalChangePoints-Snapshot (should be called when the Goal-Score changed)
     *
     * @return the GoalChangePoints-Snapshot
     */
    private ResponsePacket getGoalPointsChangeSnapshot() {
        lastScore = game.getScore();
        game.setLastDeltaBroadcast(new DeltaBroadcast(DeltaType.GOAL_POINTS_CHANGE, game.getScore()[0], game.getScore()[1]));
        return game.getSnapshotPacket();
    }


    /**
     * Gets the PhaseChange and the RoundChange-Snapshot
     *
     * @return the PhaseChange and the RoundChange-Snapshot
     */
    private ResponsePacket getPhaseAndRoundChangeSnapshot() {
        //If new Round
        if (lastPhase == PhaseType.FANPHASE) {
            game.setLastDeltaBroadcast(new DeltaBroadcast(DeltaType.ROUND_CHANGE, game.getPhase()));
        }
        //If new Phase
        else {
            game.setLastDeltaBroadcast(new DeltaBroadcast(DeltaType.PHASE_CHANGE, game.getPhase()));
        }

        lastPhase = game.getPhase();
        return game.getSnapshotPacket();
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

    /**
     * Informs the GameLoop that the protocol was violated
     */
    public void protocolViolatedBy(User user) {
        gameFinish.setProtocolViolatingUser(user);
    }

    /**
     * Increments the round-number in the game-state class FantasticFeastsGame
     * Randomly chooses the beginning team
     */
    private void startNewRound() {

        //If game is over
        if (isGameOver())
            return;

        //Increment rounds in game-state FantasticFeastsGame
        game.newRound();

        //Overlength
        int overlength = GameEngine.getMatchConfig().getMaxRounds();

        if (overlength <= game.getRoundNumber()) {
            gameFinish.activateOverlengthCondition();
            ballPhase.activateOverlengthCondition();
        }

        //Reset Phase Handlers
        ballPhase.reset();
        playerPhase.reset();
        fanPhase.reset();

    }

    /**
     * Help method to validate the player in a DeltaRequest.
     *
     * @param playerID active entity of the delta
     * @return true if identical to the currently active entity
     */
    public boolean validateTurn(EntityIdType playerID) {
        return playerID.equals(getCurrentNext().getTurn());
    }

    public boolean isGameOver() {
        return gameFinish.isGameOver();
    }

    public NextPacket getCurrentNext() {
        return currentNext;
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
