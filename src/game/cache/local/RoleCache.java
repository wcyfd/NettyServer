package game.cache.local;

import game.entity.bo.Role;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author wcy 2016年3月30日
 *
 */
public class RoleCache {
	private static Map<Integer, Role> roleIdMap = new ConcurrentHashMap<>();
	private static Map<String,Role> roleAccountMap = new ConcurrentHashMap<>();
	
	private static Set<String> roleAccountSet = new HashSet<>();
	private static Set<String> roleNameSet = new HashSet<>();	

	public static void putRole(Role role) {
		int roleId = role.getId();
		String account = role.getAccount();
		String name = role.getName();
		
		roleIdMap.put(roleId, role);
		roleAccountMap.put(account, role);
		roleAccountSet.add(account);
		roleNameSet.add(name);
	}

	public static Role getRoleById(int roleId) {
		return roleIdMap.get(roleId);
	}
	
	public static Map<Integer,Role> getRoleMap(){
		return roleIdMap;
	}
	
	public static Role getRoleByAccount(String account){
		return roleAccountMap.get(account);
	}
	
	public static Set<String> getAllAccount(){
		return roleAccountSet;
	}
	
	public static Set<String> getAllName(){
		return roleNameSet;
	}
}
