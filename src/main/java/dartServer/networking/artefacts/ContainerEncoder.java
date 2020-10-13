package dartServer.networking.artefacts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import dartServer.networking.artefacts.requests.*;
import dartServer.networking.artefacts.responses.*;

/**
 * Encodes response-objects
 */
public class ContainerEncoder {

    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static String encode(Payload payload) {
        try {
            Container container;

            if (payload instanceof AuthRequest) {
                container = new Container("authRequest", payload);
            } else if (payload instanceof CancelGameRequest) {
                container = new Container("cancelGameRequest", payload);
            } else if (payload instanceof CreateGameRequest) {
                container = new Container("createGameRequest", payload);
            } else if (payload instanceof DoThrowRequest) {
                container = new Container("doThrowRequest", payload);
            } else if (payload instanceof JoinGameRequest) {
                container = new Container("joinGameRequest", payload);
            } else if (payload instanceof LeaveGameRequest) {
                container = new Container("leaveGameRequest", payload);
            } else if (payload instanceof StartGameRequest) {
                container = new Container("startGameRequest", payload);
            } else if (payload instanceof UndoThrowRequest) {
                container = new Container("undoThrowRequest", payload);
            } else if (payload instanceof AuthResponse) {
                container = new Container("authResponse", payload);
            } else if (payload instanceof CancelGameResponse) {
                container = new Container("cancelGameResponse", payload);
            } else if (payload instanceof CreateGameResponse) {
                container = new Container("createGameResponse", payload);
            } else if (payload instanceof DoThrowResponse) {
                container = new Container("doThrowResponse", payload);
            } else if (payload instanceof JoinGameResponse) {
                container = new Container("joinGameResponse", payload);
            } else if (payload instanceof LeaveGameResponse) {
                container = new Container("leaveGameResponse", payload);
            } else if (payload instanceof StartGameResponse) {
                container = new Container("startGameResponse", payload);
            } else if (payload instanceof UndoThrowResponse) {
                container = new Container("undoThrowResponse", payload);
            } else {
                container = null;
            }

            if (container != null) {
                return gson.toJson(container, Container.class);
            }

        } catch (JsonParseException e) {
            // TODO invalid json received add log
        }

        return null;
    }

}
