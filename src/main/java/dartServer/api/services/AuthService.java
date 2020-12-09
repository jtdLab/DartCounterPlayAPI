package dartServer.api.services;

import dartServer.commons.packets.incoming.requests.AuthRequestPacket;

public class AuthService {

    public static boolean authenticate(AuthRequestPacket authRequest) {
        //String response = FireBaseRealtimeDatabase.get("users/profile/" + authRequest.getUid());
        //return response != null;
        return true;
    }

}
