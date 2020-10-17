package dartServer.gameengine.lobby;

public class User {
    private String name;
    private String lobbyName;
    private String password;
    private boolean isArtificialIntelligence;
    private String[] mods;
    private Team team;
    private boolean isPlayer;

    private Client client; // client for networking

    // --== Constructors ==--

    /**
     * User that did not send loginRequest yet
     *
     * @param client The client of the user
     */
    public User(Client client) {
        this.client = client;
    }

    /**
     * instantiates a user with unknown or empty mods
     * user is spectator, as long as team is null
     *
     * @param client                   client for networking
     * @param name                     name of the user
     * @param lobbyName                The name of the lobby
     * @param password                 password of the user
     * @param isArtificialIntelligence AI tag
     */
    public User(Client client, String name, String lobbyName, String password, boolean isArtificialIntelligence) {
        this.client = client;
        this.name = name;
        this.lobbyName = lobbyName;
        this.password = password;
        this.isArtificialIntelligence = isArtificialIntelligence;

        this.mods = null;
        this.team = null;
        this.isPlayer = false;
    }

    /**
     * instantiates a user with known mods
     *
     * @param client                   client for networking
     * @param name                     name of user
     * @param lobbyName                The name of the lobby
     * @param password                 password of the user
     * @param isArtificialIntelligence AI tag
     * @param mods                     definite list of valid game modifications
     */
    public User(Client client, String name, String lobbyName, String password, boolean isArtificialIntelligence, String[] mods) {
        this.client = client;
        this.name = name;
        this.lobbyName = lobbyName;
        this.password = password;
        this.isArtificialIntelligence = isArtificialIntelligence;
        this.mods = mods;

        this.team = null;
        this.isPlayer = false;
    }

    // --== Methods ==--

    public void sendMessage(ResponsePacket packet) {
        client.sendPacket(packet);
    }

    // --== Getter/Setter ==--

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

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

    public boolean isArtificialIntelligence() {
        return isArtificialIntelligence;
    }

    public String[] getMods() {
        return mods;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

}
