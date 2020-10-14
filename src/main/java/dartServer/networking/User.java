package dartServer.networking;

import io.netty.channel.ChannelId;

public class User {

    private String username;
    private ChannelId channelId;

    public User(String username, ChannelId channelId) {
        this.username = username;
        this.channelId = channelId;
    }

    public String getUsername() {
        return username;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

}
