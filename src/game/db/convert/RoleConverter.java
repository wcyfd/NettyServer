package game.db.convert;

import game.db.convert.base.ResultConverter;
import game.entity.bo.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleConverter implements ResultConverter<Role> {
	@Override
	public Role convert(ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("id"));
		role.setAccount(rs.getString("account"));
		role.setProperty(rs.getString("property"));
		role.setName(rs.getString("name"));
		return role;
	}
}
