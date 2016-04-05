package game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import game.db.convert.base.ResultConverter;
import game.entity.bo.Company;
import game.entity.bo.Employee;

public class CompanyConverter implements ResultConverter<Company> {

	@Override
	public Company convert(ResultSet rs) throws SQLException {
		Company company = new Company();
		company.setCompanyId(rs.getInt("companyId"));
		company.setRoleId(rs.getInt("roleId"));
		company.setEmployeeInfo(rs.getString("employeeInfo"));
		company.setType(rs.getByte("type"));
		return company;
	}

}
