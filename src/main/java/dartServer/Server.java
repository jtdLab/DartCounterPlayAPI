package dartServer;

import dartServer.model.Throw;
import dartServer.networking.Lobby;
import dartServer.networking.PlayManager;
import dartServer.networking.User;
import dartServer.networking.handlers.websocket.HTTPInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.CopyOnWriteArrayList;

enum Status{
    NOT_RUNNING, RUNNING
}

public class Server {

    public static Server instance;

    public static void main(String[] args) {
        Server server = new Server(9000);
        server.start();
    }

    private int port;
    private Status status;
    private final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    private final PlayManager playManager = new PlayManager();

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public Server(int port) {
        this.port = port;
        status = Status.NOT_RUNNING;
    }

    public void start() {
        if(status == Status.NOT_RUNNING) {
            instance = this;

            // Configure the server.
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.option(ChannelOption.SO_BACKLOG, 1024);
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new HTTPInitializer());

                Channel ch = b.bind(port).sync().channel();

                ch.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

            status = Status.RUNNING;
            // TODO log server started
        }
        // TODO log couldn't start server
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        instance = null;
        // TODO log server stopped
    }

    public void createUser(String username, Channel channel) {
        User user = new User(username, channel);
        users.add(user);
    }

    public boolean createLobby(User user) {
        if(users.contains(user)) {
            playManager.create(user);
            return true;
        }
        return false;
    }

    public boolean deleteLobby(User user) {
        Lobby lobby = playManager.getLobby(user);
        if(lobby != null) {
            playManager.cancel(user, lobby);
            return true;
        }
        return false;
    }

    public boolean joinLobby(User user, Lobby lobby) {
        Lobby l = playManager.getLobby(user);
        if(l == null) {
            return playManager.join(user, lobby);
        }
        return false;
    }

    public boolean leaveLobby(User user) {
        Lobby lobby = playManager.getLobby(user);
        if(lobby != null) {
            playManager.leave(user,lobby);
            return true;
        }
        return false;
    }

    public boolean startGame(User user)  {
        Lobby lobby = playManager.getLobby(user);
        if(lobby != null) {
            return playManager.start(user,lobby);
        } else {
            return false;
        }
    }

    public boolean doThrow(Throw t, User user) {
        Lobby lobby = playManager.getLobby(user);
        if(lobby != null) {
            return playManager.doThrow(t, user, lobby);
        } else {
            return false;
        }
    }

    public boolean undoThrow(User user)  {
        Lobby lobby = playManager.getLobby(user);
        if(lobby != null) {
            return playManager.undoThrow(user, lobby);
        } else {
            return false;
        }
    }



    public User getUser(Channel channel) {
        for (User user : users) {
            if (user.getChannel() == channel) {
                return user;
            }
        }
        return null;
    }

    public PlayManager getPlayManager() {
        return playManager;
    }
}