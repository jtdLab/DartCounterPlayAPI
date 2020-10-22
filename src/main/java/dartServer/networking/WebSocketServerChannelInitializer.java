package dartServer.networking;

import dartServer.networking.codec.ContainerToJsonEncoder;
import dartServer.networking.codec.ContainerToPacketDecoder;
import dartServer.networking.codec.JsonToContainerDecoder;
import dartServer.networking.codec.PacketToContainerEncoder;
import dartServer.networking.handler.WebSocketServerHandler;
import dartServer.networking.handler.WebsocketHandshakeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Initialize the {@link Channel} by adding encoders, decoders and handlers to the {@link ChannelPipeline}
 */
public class WebSocketServerChannelInitializer extends ChannelInitializer<Channel> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        pipeline.addLast("websocketHandshakeHandler", new WebsocketHandshakeHandler());

        // Decoders
        pipeline.addLast("jsonDecoder", new JsonToContainerDecoder());
        //pipeline.addLast("containerValidator", new IncomingPacketContainerValidator());
        pipeline.addLast("containerDecoder", new ContainerToPacketDecoder());

        // Encoders
        pipeline.addLast("jsonEncoder", new ContainerToJsonEncoder());
        pipeline.addLast("containerEncoder", new PacketToContainerEncoder());

        // Handlers
        pipeline.addLast("websocketHandler", new WebSocketServerHandler());
    }

}
