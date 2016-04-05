package game.module.employee.service;

import game.entity.bo.Employee;

import java.util.List;

/**
 * 
 * @author wcy 2016年3月31日
 *
 */
public interface EmployeeService {
	
	public void initService();
	
	public List<Employee> createEmployee(int number,long currentTime);
	
	public void initEmployeeAttribute(Employee employee,long currentTime);

}
