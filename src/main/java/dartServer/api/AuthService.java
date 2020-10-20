package dartServer.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dartServer.commons.packets.incoming.requests.AuthRequestPacket;
import dartServer.commons.packets.outgoing.unicasts.AuthResponsePacket;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {

    private static final String protocol = "http";
    private static final String host = "127.0.0.1"; // "192.168.2.110"; //
    private static final int port = 8000;

    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static boolean authenticate(AuthRequestPacket authRequest) {
        URI uri = URI.create(AuthService.protocol + "://" + AuthService.host + ":" + AuthService.port + "/api/auth");

        String body = gson.toJson(authRequest, AuthRequestPacket.class);

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();


        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response != null) {
            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), AuthResponsePacket.class).getSuccessful();
            }
        }

        return false;
    }

}
