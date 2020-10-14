package dartServer;

import dartServer.model.Game;
import dartServer.model.Throw;
import dartServer.networking.Lobby;
import dartServer.networking.PlayManager;
import dartServer.networking.User;
import dartServer.networking.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.CopyOnWriteArrayList;

enum Status{
    NOT_RUNNING, RUNNING
}

public class Server {

    private int port;
    private Status status;

    private final ChannelGroup channels;
    private final CopyOnWriteArrayList<User> users;

    private final PlayManager playManager;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public Server(int port) {
        this.port = port;
        status = Status.NOT_RUNNING;
        channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        users = new CopyOnWriteArrayList<>();
        playManager = new PlayManager();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
    }

    public void start() {
        if(status == Status.NOT_RUNNING) {

            // Configure the server.
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.option(ChannelOption.SO_BACKLOG, 1024);
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ServerInitializer(this));
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
        } else {
            // TODO log alrdy running
        }
    }

    public void stop() {
        if(status == Status.RUNNING) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            // TODO log server stopped
        } else {
            // TODO log server not running
        }
    }


    public void createUser(String username, Channel channel) {
        User user = new User(username, channel.id());
        users.add(user);
        channels.add(channel);
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

    public boolean joinLobby(User user, String usernameOfPlayerInLobby) {
        Lobby l = playManager.getLobby(user);
        if(l == null) {
            Lobby lobby = playManager.getLobby(usernameOfPlayerInLobby);
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


    public Game getGame(User user) {
       return playManager.getGame(user);
    }


    public User getUser(Channel channel) {
        for (User user : users) {
            if (channels.find(user.getChannelId()) == channel) {
                return user;
            }
        }
        return null;
    }

}