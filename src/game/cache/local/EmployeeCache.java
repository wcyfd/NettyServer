package game.cache.local;

import game.entity.bo.Employee;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeCache {
	private static Map<Integer, Employee> employeeMap = new ConcurrentHashMap<>();
	private static Map<Integer, Employee> noJobEmployeeMap = new ConcurrentHashMap<>();

	public static void pushEmployee(Employee e) {
		int employeeId = e.getEmployeeId();
		int companyId = e.getCompanyId();
		int roleId = e.getRoleId();

		employeeMap.put(employeeId, e);
		if (companyId == 0 || roleId == 0) {
			noJobEmployeeMap.put(employeeId, e);
		}

	}
}
