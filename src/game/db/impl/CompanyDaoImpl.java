package game.db.impl;

import game.db.access.DataAccess;
import game.db.convert.CompanyConverter;
import game.db.convert.EmployeeConverter;
import game.db.convert.base.IntegerConverter;
import game.db.dao.CompanyDao;
import game.entity.bo.Company;
import game.entity.bo.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

/**
 * 
 * @author wcy 2016年3月31日
 *
 */
public class CompanyDaoImpl extends DataAccess implements CompanyDao {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int insertCompanyNotCloseConnection(Company company,Connection conn) {
		int id = 0;
		final String sql = "insert into company(companyId,roleId,type,employeeInfo) values(?,?,?,?)";
		try {
			id = super.insertNotCloseConn(sql, new IntegerConverter(), conn, null, company.getRoleId(), company.getType(),
					company.getEmployeeInfo());
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public int insertCompany(Company company) {
		int id = 0;
		final String sql = "insert into company(companyId,roleId,type,employeeInfo) values(?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			id = super.insert(sql, new IntegerConverter(), conn, null, company.getRoleId(), company.getType(),
					company.getEmployeeInfo());
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
		
	}

	@Override
	public void updateCompany(Company company) {
		final String sql = "update company set roleId=?,type=?,employeeInfo=? where companyId=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, company.getRoleId(), company.getType(), company.getEmployeeInfo(),
					company.getCompanyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Company getCompanyByRoleId(int roleId) {
		String sql = "select * from company where roleId=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			Company result = super.queryForObject(sql, new CompanyConverter(), conn, roleId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Company getCompanyByCompanyId(int companyId) {
		String sql = "select * from company where companyId=?";
		try{
			Connection conn = dataSource.getConnection();
			Company company = super.queryForObject(sql, new CompanyConverter(), conn, companyId);
			return company;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}










