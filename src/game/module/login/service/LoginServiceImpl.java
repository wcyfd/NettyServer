package game.module.login.service;

import game.cache.local.ContextCache;
import game.cache.local.RoleCache;
import game.common.ErrorCode;
import game.db.dao.RoleDao;
import game.entity.bo.Role;
import game.module.login.LoginConstant;
import game.net.message.Message;
import game.net.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;

public class LoginServiceImpl implements LoginService {
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public NettyMessage login(ChannelHandlerContext context, String account) {
		NettyMessage message = Message.create();
		message.putInt(LoginConstant.LOGIN);
		Role role = RoleCache.getRoleByAccount(account);
		if (role == null) {
			role = roleDao.getRoleByAccount(account);
			if (role == null) {
				message.putInt(ErrorCode.NO_ROLE);
				return message;
			} else {
				this.loginInit(role);
				RoleCache.putRole(role);
			}
		}
		ContextCache.bindRoleAndChannel(role, context);
		
		message.putInt(ErrorCode.SUCCESS);
		return message;
	}
	
	private void loginInit(Role role) {
	}
}
