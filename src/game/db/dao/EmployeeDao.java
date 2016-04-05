package game.db.dao;

import game.entity.bo.Employee;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface EmployeeDao {
	public int insertEmployee(Employee e);
	
	public void updateEmployee(Employee e);
	
	public Map<Employee,Integer> insertEmployeesNotCloseConnection(List<Employee> employeeList,Connection conn);
	
	public List<Employee> getAllEmployee();
}
