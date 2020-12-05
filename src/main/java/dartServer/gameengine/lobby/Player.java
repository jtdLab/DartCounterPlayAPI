package dartServer.gameengine.lobby;

import dartServer.commons.artifacts.PlayerSnapshot;
import dartServer.commons.packets.outgoing.ResponsePacket;
import dartServer.networking.Client;

public class Player {

    private final String name; // unique name

    private boolean isNext;

    private Integer lastThrow;
    private int pointsLeft;
    private int dartsThrown;

    private Integer sets;
    private int legs;

    private String average;
    private String checkoutPercentage;

    private int firstNine;

    private int fourtyPlus;
    private int sixtyPlus;
    private int eightyPlus;
    private int hundredPlus;
    private int hundredTwentyPlus;
    private int hundredFourtyPlus;
    private int hundredSixtyPlus;
    private int hundredEighty;

    private Integer lobbyId; // id of lobby the player is part of or null

    private Client client; // client for networking

    private boolean playing;


    // --== Constructors ==--

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Client client) {
        this.name = name;
        this.client = client;
    }


    // --== Methods ==--

    public void sendMessage(ResponsePacket packet) {
        client.sendPacket(packet);
    }


    // --== Getter/Setter ==--

    public PlayerSnapshot getSnapshot() {
        return new PlayerSnapshot(this);
    }

    public boolean isConnected() {
        return client != null;
    }

    public String getName() {
        return name;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public Integer getLastThrow() {
        return lastThrow;
    }

    public void setLastThrow(Integer lastThrow) {
        this.lastThrow = lastThrow;
    }

    public int getPointsLeft() {
        return pointsLeft;
    }

    public void setPointsLeft(int pointsLeft) {
        this.pointsLeft = pointsLeft;
    }

    public int getDartsThrown() {
        return dartsThrown;
    }

    public void setDartsThrown(int dartsThrown) {
        this.dartsThrown = dartsThrown;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getCheckoutPercentage() {
        return checkoutPercentage;
    }

    public void setCheckoutPercentage(String checkoutPercentage) {
        this.checkoutPercentage = checkoutPercentage;
    }

    public int getFirstNine() {
        return firstNine;
    }

    public void setFirstNine(int firstNine) {
        this.firstNine = firstNine;
    }

    public int getFourtyPlus() {
        return fourtyPlus;
    }

    public void setFourtyPlus(int fourtyPlus) {
        this.fourtyPlus = fourtyPlus;
    }

    public int getSixtyPlus() {
        return sixtyPlus;
    }

    public void setSixtyPlus(int sixtyPlus) {
        this.sixtyPlus = sixtyPlus;
    }

    public int getEightyPlus() {
        return eightyPlus;
    }

    public void setEightyPlus(int eightyPlus) {
        this.eightyPlus = eightyPlus;
    }

    public int getHundredPlus() {
        return hundredPlus;
    }

    public void setHundredPlus(int hundredPlus) {
        this.hundredPlus = hundredPlus;
    }

    public int getHundredTwentyPlus() {
        return hundredTwentyPlus;
    }

    public void setHundredTwentyPlus(int hundredTwentyPlus) {
        this.hundredTwentyPlus = hundredTwentyPlus;
    }

    public int getHundredFourtyPlus() {
        return hundredFourtyPlus;
    }

    public void setHundredFourtyPlus(int hundredFourtyPlus) {
        this.hundredFourtyPlus = hundredFourtyPlus;
    }

    public int getHundredSixtyPlus() {
        return hundredSixtyPlus;
    }

    public void setHundredSixtyPlus(int hundredSixtyPlus) {
        this.hundredSixtyPlus = hundredSixtyPlus;
    }

    public int getHundredEighty() {
        return hundredEighty;
    }

    public void setHundredEighty(int hundredEighty) {
        this.hundredEighty = hundredEighty;
    }

    public Integer getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Integer lobbyId) {
        this.lobbyId = lobbyId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lobbyId=" + lobbyId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.getName());
    }

}
