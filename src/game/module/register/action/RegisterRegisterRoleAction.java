package game.module.register.action;

import game.module.register.service.RegisterService;
import game.nav.AbstractAction;
import game.net.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class RegisterRegisterRoleAction extends AbstractAction{

	private RegisterService registerService;
	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
	@Override
	public NettyMessage execute(NettyMessage message, ChannelHandlerContext handler) {
		String account = message.getString();
		String name = message.getString();
		message = registerService.registerRole(handler, account, name);
		
		if(message == null){
			return null;
		}

		handler.writeAndFlush(message.getData());
		return message;
	}

}
