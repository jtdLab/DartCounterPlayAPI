package dartServer.networking;

import dartServer.networking.artefacts.ContainerEncoder;
import dartServer.networking.artefacts.Payload;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class User {

    private String username;
    private Channel channel;

    public User(String username, Channel channel) {
        this.username = username;
        this.channel = channel;
    }

    public String getUsername() {
        return username;
    }

    public Channel getChannel() {
        return channel;
    }

    public void send(Payload payload) {
        channel.writeAndFlush(new TextWebSocketFrame(ContainerEncoder.encode(payload)));
    }

    public void send(Payload ... payloads) {
        for(Payload payload : payloads) {
            send(payload);
        }
    }

}
