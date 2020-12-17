package dartServer.api.services;

import dartServer.api.helpers.FireBaseRealtimeDatabase;

public class IsOnlineService {

    public static void updateIsOnline(String uid, boolean isOnline) {
        String body = "{\"isOnline\":" + isOnline + "}";
        FireBaseRealtimeDatabase.patch("users/" + uid, body);
    }
}
