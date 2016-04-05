package game.net;

import game.cache.local.ContextCache;
import game.nav.AbstractAction;
import game.nav.Nav;
import game.net.message.Message;
import game.net.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class ServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server active");

		NettyMessage message = Message.create();

		message.putInt(10);
		for (int i = 0; i < 10; i++) {
			message.putString("this is server , do you copy");
			message.putInt(20);
			message.putBoolean(true);
			message.putByte((byte) 127);
			message.putDouble(20.5);
			message.putFloat(10.33f);
		}

		ctx.writeAndFlush(message.getByteBuf());

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		System.out.println("server complete");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;

		System.out.println("server read");
		NettyMessage message = Message.create();
		message.setByteBuf(buf);

		int protocalNum = message.getInt();
		try {
			AbstractAction action = Nav.navMap().get(protocalNum);

			if (action == null)
				throw new NullPointerException("没有该协议");

			message = action.execute(message, ctx);
			ctx.writeAndFlush(message.getByteBuf());

			PrintMessage.printMessage(message);
		} catch (Exception e) {
			System.err.println("接受协议出错");
		}

	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {		
		ContextCache.getRoleByContext(ctx);
		super.disconnect(ctx, promise);
		System.out.println("disconnect");
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		System.out.println("channelRegistered");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		System.out.println("channelUnregistered");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		ctx.close();
	}

}
