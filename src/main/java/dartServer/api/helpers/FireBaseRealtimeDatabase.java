package dartServer.api.helpers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Interface for the FireBaseRealtimeDatabase REST API
 */
public class FireBaseRealtimeDatabase {

    private static final String protocol = "https";
    private static final String host = "dartcounterapi.firebaseio.com";

    public static String get(String path) {
        URI uri = URI.create(FireBaseRealtimeDatabase.protocol + "://" + FireBaseRealtimeDatabase.host + "/" + path + ".json");

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
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
            if (response.statusCode() == 200 && !response.body().equals("null")) {
                return response.body();
            }
        }

       return null;
    }

    public static boolean put(String path, String body) {
        URI uri = URI.create(FireBaseRealtimeDatabase.protocol + "://" + FireBaseRealtimeDatabase.host + "/" + path + ".json");

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body))
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
            if (response.statusCode() == 200 && response.body().trim().equals(body.trim())) {
               return true;
            }
        }

       return false;
    }

    public static boolean patch(String path, String body) {
        URI uri = URI.create(FireBaseRealtimeDatabase.protocol + "://" + FireBaseRealtimeDatabase.host + "/" + path + ".json");

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
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
            if (response.statusCode() == 200 && response.body().trim().equals(body.trim())) {
                return true;
            }
        }

        return false;
    }

    public static boolean delete(String path) {
        URI uri = URI.create(FireBaseRealtimeDatabase.protocol + "://" + FireBaseRealtimeDatabase.host + "/" + path + ".json");

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .DELETE()
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
            if (response.statusCode() == 200 && response.body().trim().equals("null")) {
                return true;
            }
        }

        return false;
    }
}
