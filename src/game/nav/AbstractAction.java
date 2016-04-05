package game.nav;

import game.net.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 动作基类
 * @author wcy 2016年3月30日
 *
 */
public abstract class AbstractAction {
	public abstract NettyMessage execute(NettyMessage message ,ChannelHandlerContext handler);
}
