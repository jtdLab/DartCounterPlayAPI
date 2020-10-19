package dartServer.gameengine.lobby;

import dartServer.commons.artifacts.PlayerSnapshot;
import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.commons.validators.Username;
import dartServer.gameengine.model.Player;
import dartServer.networking.Client;

public class User {

    @Username
    private String name;
    private String password;

    private boolean isPlayer;

    private long lobbyId;

    private Client client; // client for networking

    // PLAY DATA
    private Player player;

    // --== Constructors ==--

    /**
     * User that did not send loginRequest yet
     *
     * @param client The client of the user
     */
    public User(String name, Client client) {
        this.name = name;
        lobbyId = -1;
        this.client = client;
    }

    // --== Methods ==--

    public void sendMessage(ResponsePacket packet) {
        client.sendPacket(packet);
    }

    // --== Getter/Setter ==--

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public boolean isConnected() {
        return client != null;
    }

    public long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public boolean isNext() {
        return player.isNext();
    }

    public void setNext(boolean next) {
        player.setNext(next);
    }

    public int getLastThrow() {
        return player.getLastThrow();
    }

    public void setLastThrow(int lastThrow) {
        player.setLastThrow(lastThrow);
    }

    public int getPointsLeft() {
        return player.getPointsLeft();
    }

    public void setPointsLeft(int pointsLeft) {
        player.setPointsLeft(pointsLeft);
    }

    public int getDartsThrown() {
        return player.getDartsThrown();
    }

    public void setDartsThrown(int dartsThrown) {
        player.setDartsThrown(dartsThrown);
    }

    public int getSets() {
        return player.getSets();
    }

    public void setSets(int sets) {
        player.setSets(sets);
    }

    public int getLegs() {
        return player.getLegs();
    }

    public void setLegs(int legs) {
        player.setLegs(legs);
    }

    public String getAverage() {
        return player.getAverage();
    }

    public void setAverage(String average) {
        player.setAverage(average);
    }

    public String getCheckoutPercentage() {
        return player.getCheckoutPercentage();
    }

    public void setCheckoutPercentage(String checkoutPercentage) {
        player.setCheckoutPercentage(checkoutPercentage);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerSnapshot getSnapshot() {
        return player.getSnapshot();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lobbyId=" + lobbyId +
                '}';
    }
}
