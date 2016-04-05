package game.module.role.service;

import game.entity.bo.Role;

/**
 * 
 * @author wcy 2016年3月30日
 *
 */
public interface RoleService {
	/**
	 * 
	 * @param role
	 * @param num
	 * @author wcy 2016年3月30日
	 */
	public void addRoleGold(Role role,int num);
	
	/**
	 * 
	 * @param role
	 * @param num
	 * @author wcy 2016年3月30日
	 */
	public void addRoleMoney(Role role,int num);
	
	/**
	 * 
	 * @param role
	 * @param num
	 * @author wcy 2016年3月30日
	 */
	public void addRolePrestige(Role role,int num);
	
	/**
	 * 
	 * 
	 * @author wcy 2016年4月1日
	 */
	public void initService();
}
