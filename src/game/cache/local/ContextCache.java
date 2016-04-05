package game.cache.local;

import game.entity.bo.Role;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author wcy 2016年3月30日
 *
 */
public class ContextCache {
	private static Map<ChannelHandlerContext,Role> map = new ConcurrentHashMap<>();
	
	public static void bindRoleAndChannel(Role role,ChannelHandlerContext context){
		map.put(context,role);
	}
	
	
	public static Role getRoleByContext(ChannelHandlerContext context){ 
		return map.get(context);
	}
}
