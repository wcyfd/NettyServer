package game.db.convert.base;

import java.sql.ResultSet;
import java.sql.SQLException;


public class IntegerConverter implements ResultConverter<Integer> {
	public Integer convert(ResultSet rs) throws SQLException {
		return rs.getInt(1);
	}

}