package game.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @author wcy 2016年3月30日
 *
 */
public class ServerFramework {

	public void setPort(int port) {
		this.port = port;
	}

	public void setBuffer(int buffer) {
		this.buffer = buffer;
	}

	public void start() {
		EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
		EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			
			b.group(bossLoopGroup, workerLoopGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, buffer).handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipe = ch.pipeline();
							pipe.addLast("encoder",new LengthFieldPrepender(4));
							pipe.addLast("decoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
							pipe.addLast("handler",new ServerHandler());
						}

					});

			ChannelFuture f = b.bind(this.port).sync();
			f.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossLoopGroup.shutdownGracefully();
			workerLoopGroup.shutdownGracefully();
		}
	}

	private int port = 0;
	private int buffer = 1024;
}
