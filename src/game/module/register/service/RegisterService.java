package game.module.register.service;

import io.netty.channel.ChannelHandlerContext;
import game.net.message.NettyMessage;


/**
 * 注册服务
 * @author wcy 2016年3月29日
 *
 */
public interface RegisterService {
	public NettyMessage registerRole(ChannelHandlerContext context,String account,String name);
}
