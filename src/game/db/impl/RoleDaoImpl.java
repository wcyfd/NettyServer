package game.db.impl;

import game.db.access.DataAccess;
import game.db.convert.RoleConverter;
import game.db.convert.base.IntegerConverter;
import game.db.convert.base.StringConverter;
import game.db.dao.RoleDao;
import game.entity.bo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


public class RoleDaoImpl extends DataAccess implements RoleDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int insertRoleNotCloseConnection(Role role, Connection conn) {
		int id = 0;
		final String sql = "insert into role(id,name,account,property)" + "values(?,?,?,?)";
		try {
			id = super.insertNotCloseConn(sql, new IntegerConverter(), conn, null, role.getName(), role.getAccount(),
					role.getProperty());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public void updateRole(Role role) {
		final String sql = "update role set name=?,account=?,property=?" + " where id=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, role.getName(), role.getAccount(), role.getProperty(), role.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Role getRoleByAccount(String account) {
		final String sql = "select * from role where account=? ";
		try {
			Connection conn = dataSource.getConnection();
			Role result = super.queryForObject(sql, new RoleConverter() , conn, account);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Role getRoleById(int id) {
		final String sql = "select * from role where id=? ";
		try {
			Connection conn = dataSource.getConnection();
			Role result = super.queryForObject(sql, new RoleConverter() , conn, id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> getAllAccount() {
		String sql = "select account from role ";
		try {
			Connection conn = dataSource.getConnection();
			List<String> result = super.queryForList(sql, new StringConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> getAllName() {
		String sql = "select name from role ";
		try {
			Connection conn = dataSource.getConnection();
			List<String> result = super.queryForList(sql, new StringConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
