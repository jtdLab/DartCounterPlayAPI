package dartServer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import dartServer.gameengine.GameEngine;
import dartServer.gameengine.lobby.Lobby;
import dartServer.gameengine.lobby.User;
import dartServer.networking.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

// ws://46.101.130.16:9000
// curl -i -N -H "Connection: Upgrade" -H "Upgrade: websocket" -H "Host: 46.101.130.16:9000" -H "Origin: http://www.websocket.org" "46.101.130.16:9000"
// curl -i -N -H "Connection: Upgrade" -H "Upgrade: websocket" -H "Host: 127.0.0.1:9000" -H "Origin: http://www.websocket.org" "127.0.0.1:9000"
// {"payloadType":"authRequest","payload":{"uid":"LVi4ZRQMskgTYG2StmNFUXxN0yW2","username":"mrjosch"},"timestamp":"2020-10-17 03:38:16.44"}
// {"payloadType":"createGame","payload":{},"timestamp":"2020-10-17 03:38:16.44"}
// {"payloadType":"joinGame","payload":{"gameCode":1000},"timestamp":"2020-10-17 03:38:16.44"}
// {"payloadType":"startGame","payload":{},"timestamp":"2020-10-17 03:38:16.44"}
// {"payloadType":"cancelGame","payload":{},"timestamp":"2020-10-17 03:38:16.44"}
// {"payloadType":"exitGame","payload":{},"timestamp":"2020-10-17 03:38:16.44"}
// {"payloadType":"performThrow","payload":{"t":{"points":180,"dartsOnDouble":0,"dartsThrown":3,"userIndex":0}},"timestamp":"2020-10-17 03:38:16.44"}
// {"payloadType":"undoThrow","payload":{},"timestamp":"2020-10-17 03:38:16.44"}

/**
 * This is the main class to start the Dart server from the command line
 * Commandline Options are implemented with picocli
 * <p>
 * See https://picocli.info/
 */
@CommandLine.Command(name = "DartServer", version = "1.0")
class DartServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DartServer.class);

    @CommandLine.Option(names = {"-p", "--port"}, description = "Server Port (default=9000)")
    private static int port = 9000;

    /**
     * Start the server by parsing the commandline options
     *
     * @param args Run arguments
     */
    public static void main(String... args) {
        System.out.println(
                " _____             _    _____                          \n" +
                        "|  __ \\           | |  / ____|                         \n" +
                        "| |  | | __ _ _ __| |_| (___   ___ _ ____   _____ _ __ \n" +
                        "| |  | |/ _` | '__| __|\\___ \\ / _ \\ '__\\ \\ / / _ \\ '__|\n" +
                        "| |__| | (_| | |  | |_ ____) |  __/ |   \\ V /  __/ |   \n" +
                        "|_____/ \\__,_|_|   \\__|_____/ \\___|_|    \\_/ \\___|_|   \n" +
                        "                                                       "
        );

        CommandLine.run(new DartServer(), System.out);
    }

    /**
     * Initialize the server
     */
    public void run() {
        System.out.println();
        System.out.println("Starting with options:");
        System.out.println("> Server port: " + port);
        System.out.println();

        // Use a service account
        try {
            InputStream serviceAccount = new FileInputStream("./src/main/resources/serviceAccount.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        logger.info("Initializing game engine...");
        GameEngine.init();

        logger.info("Starting monitoring ...");
        Thread thread = new Thread(() -> {
            while(true) {
                printServerState();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            logger.info("Starting websocket server...");
            new WebSocketServer(port).run();
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private void printServerState() {
        logger.info("");
        logger.info("======================================");
        logger.info("============ SERVER STATE ============");
        logger.info("======================================");
        logger.info("Players: " + GameEngine.getUsers().length + " (online)");
        for (User user : GameEngine.getUsers()) {
            logger.info(user.toString());
        }
        logger.info("======================================");
        logger.info("Lobbies: " + GameEngine.getLobbies().size() + " (active)");
        for (Lobby lobby : GameEngine.getLobbies()) {
            logger.info(lobby.toString());
        }
        logger.info("======================================");
        logger.info("======================================");
        logger.info("======================================");
        logger.info("");
    }
}