package game.db.dao;


import game.entity.bo.Role;

import java.sql.Connection;
import java.util.List;

/**
 * 
 * @author wcy 2016年3月30日
 *
 */
public interface RoleDao {
	public int insertRoleNotCloseConnection(Role role,Connection conn);
	
	public void updateRole(Role role);
	
	public Role getRoleById(int id);
	
	public Role getRoleByAccount(String account);
	
	public List<String> getAllAccount();
	
	public List<String> getAllName();
}
