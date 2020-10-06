package dartServer.model;

public class GameLoop extends Thread {

    private Game game;

    public GameLoop(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (game.getWinner() == null) {

        }
    }
}
