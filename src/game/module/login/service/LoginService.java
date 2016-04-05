package game.module.login.service;

import game.entity.bo.Role;
import game.net.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;

public interface LoginService {
	public NettyMessage login(ChannelHandlerContext context,String account);
}
