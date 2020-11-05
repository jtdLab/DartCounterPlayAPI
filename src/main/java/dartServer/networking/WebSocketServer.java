package dartServer.networking;

import dartServer.networking.codec.*;
import dartServer.networking.handler.Lo;
import dartServer.networking.handler.WebSocketServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

public class WebSocketServer {

    /**
     * The server port
     */
    private final int port;

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
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // webSocket
                            pipeline.addLast("lo", new Lo());
                            pipeline.addLast("httpServerCodec", new HttpServerCodec());
                            pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
                            pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());

                            pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("", true));

                            // Decoders
                            pipeline.addLast("jsonDecoder", new JsonToContainerDecoder());
                            pipeline.addLast("containerValidator", new IncomingPacketContainerValidator());
                            pipeline.addLast("containerDecoder", new ContainerToPacketDecoder());

                            // Encoders
                            pipeline.addLast("jsonEncoder", new ContainerToJsonEncoder());
                            pipeline.addLast("containerEncoder", new PacketToContainerEncoder());

                            // Handlers
                            pipeline.addLast("websocketHandler", new WebSocketServerHandler());
                        }
                    });

            Channel ch = b.bind(new InetSocketAddress("0.0.0.0", Integer.valueOf(port))).sync().channel();

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
