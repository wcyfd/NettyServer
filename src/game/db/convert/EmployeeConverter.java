package game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import game.db.convert.base.ResultConverter;
import game.entity.bo.Employee;

public class EmployeeConverter implements ResultConverter<Employee> {

	@Override
	public Employee convert(ResultSet rs) throws SQLException {
		Employee employee = new Employee();
		employee.setEmployeeId(rs.getInt("employeeId"));
		employee.setCompanyId(rs.getInt("companyId"));
		employee.setRoleId(rs.getInt("roleId"));
		employee.setBirthTime(rs.getLong("birthTime"));
		employee.setName(rs.getString("name"));
		employee.setTalent(rs.getString("talent"));
		employee.setTech(rs.getString("tech"));
		employee.setSalary(rs.getInt("salary"));
		employee.setStatus(rs.getByte("status"));

		return employee;
	}

}
