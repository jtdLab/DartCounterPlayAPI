package dartServer.gameengine.model.enums;

import com.google.gson.annotations.SerializedName;

public enum GameStatus {
    @SerializedName("pending")
    PENDING,

    @SerializedName("running")
    RUNNING,

    @SerializedName("finished")
    FINISHED
}
