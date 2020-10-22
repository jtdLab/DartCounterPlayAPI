package dartServer.networking;

import dartServer.networking.codec.*;
import dartServer.networking.handler.HttpServerHandler;
import dartServer.networking.handler.WebSocketServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;


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

        // Decoders
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());

        pipeline.addLast("httpServerHandler", new HttpServerHandler());
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
