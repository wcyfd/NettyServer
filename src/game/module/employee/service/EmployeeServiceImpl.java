package game.module.employee.service;

import game.cache.local.EmployeeCache;
import game.db.dao.EmployeeDao;
import game.entity.bo.Employee;
import game.entity.bo.Role;
import game.module.employee.EmployeeConstant;
import game.utils.Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

/**
 * 
 * @author wcy 2016年3月31日
 *
 */
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao employeeDao;

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void initService() {
		List<Employee> list = employeeDao.getAllEmployee();
		for (Employee e : list) {
			EmployeeCache.pushEmployee(e);
		}

	}

	@Override
	public List<Employee> createEmployee(int number,long currentTime) {
		List<Employee> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Employee employee = new Employee();
			this.initEmployeeAttribute(employee,currentTime);
			list.add(employee);
		}

		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			Map<Employee, Integer> resultMap = employeeDao.insertEmployeesNotCloseConnection(list, conn);
			conn.commit();
			conn.setAutoCommit(true);

			for (Entry<Employee, Integer> entry : resultMap.entrySet()) {
				Employee e = entry.getKey();
				int id = entry.getValue();
				e.setEmployeeId(id);
				EmployeeCache.pushEmployee(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void initEmployeeAttribute(Employee employee,long currentTime) {
		long birthTime = currentTime;
		String name = Utils.getRandomEmployeeName();
		employee.setName(name);
		employee.setBirthTime(birthTime);
		employee.setRoleId(EmployeeConstant.INIT_ROLE);
		employee.setCompanyId(EmployeeConstant.INIT_COMPANY);
	}
	

}
