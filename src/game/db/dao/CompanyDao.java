package game.db.dao;

import game.entity.bo.Company;

import java.sql.Connection;

/**
 * 
 * @author wcy 2016年4月1日
 *
 */
public interface CompanyDao {
	public int insertCompanyNotCloseConnection(Company company,Connection conn);
	
	public int insertCompany(Company company);
	
	public void updateCompany(Company company);
	
	public Company getCompanyByRoleId(int roleId);
	
	public Company getCompanyByCompanyId(int companyId);
}
