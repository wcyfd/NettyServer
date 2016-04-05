package game.db.impl;

import game.db.access.DataAccess;
import game.db.convert.EmployeeConverter;
import game.db.convert.base.IntegerConverter;
import game.db.dao.EmployeeDao;
import game.entity.bo.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class EmployeeDaoImpl extends DataAccess implements EmployeeDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int insertEmployee(Employee employee) {
		int id = 0;
		final String sql = "insert into employee(employeeId,roleId,companyId,name,birthTime,talent,tech,salary,status)" 
				+ "values(?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = dataSource.getConnection();
			id = super.insert(sql, new IntegerConverter() , conn,null,
					employee.getRoleId(),employee.getCompanyId(),employee.getName(),employee.getBirthTime(),
					employee.getTalent(),employee.getTech(),employee.getSalary(),employee.getStatus());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public Map<Employee,Integer> insertEmployeesNotCloseConnection(List<Employee> employeeList,Connection conn) {

		Map<Employee,Integer> employeeIdMap = new HashMap<>();
		for (Employee employee : employeeList) {
			int id = 0;
			final String sql = "insert into employee(employeeId,roleId,companyId,name,birthTime,talent,tech,salary,status)" 
					+ "values(?,?,?,?,?,?,?,?,?)";
			try {
				id = super.insertNotCloseConn(sql, new IntegerConverter(), conn, null, employee.getRoleId(),
						employee.getCompanyId(), employee.getName(), employee.getBirthTime(), employee.getTalent(),
						employee.getTech(),employee.getSalary(),employee.getStatus());
				employeeIdMap.put(employee,id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 

		return employeeIdMap;
	}

	@Override
	public void updateEmployee(Employee employee) {
		final String sql = "update employee set roleId=?,companyId=?,name=?,birthTime=?,talent=?,tech=?,salary=?,status=? where employeeId=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, employee.getRoleId(), employee.getCompanyId(), employee.getName(),
					employee.getBirthTime(), employee.getTalent(), employee.getTech(),employee.getSalary(),employee.getStatus(), employee.getEmployeeId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Employee> getAllEmployee() {
		String sql = "select * from employee";
		try {
			Connection conn = dataSource.getConnection();
			List<Employee> result = super.queryForList(sql, new EmployeeConverter() ,conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
