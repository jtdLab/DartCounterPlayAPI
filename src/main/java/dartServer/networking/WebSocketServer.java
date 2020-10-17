package dartServer.networking;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class WebSocketServer {

    /**
     * The server port
     */
    private int port;

    /**
     * @param port The port the server should run on
     */
    public WebSocketServer(int port) {
        this.port = port;
    }

    /**
     * Start the web socket server
     *
     * @throws Exception Any exception that occurs during initializing of the server
     */
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new WebSocketServerChannelInitializer());

            Channel ch = b.bind(port).sync().channel();

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * Get the port of the server
     *
     * @return Server port
     */
    public int getPort() {
        return port;
    }

}
