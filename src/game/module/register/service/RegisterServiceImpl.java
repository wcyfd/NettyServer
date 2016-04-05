package game.module.register.service;

import game.cache.local.CompanyCache;
import game.cache.local.ContextCache;
import game.cache.local.EmployeeCache;
import game.cache.local.RoleCache;
import game.common.ErrorCode;
import game.db.dao.CompanyDao;
import game.db.dao.EmployeeDao;
import game.db.dao.RoleDao;
import game.entity.bo.Company;
import game.entity.bo.Employee;
import game.entity.bo.Role;
import game.module.employee.service.EmployeeService;
import game.module.register.RegisterConstant;
import game.net.message.Message;
import game.net.message.NettyMessage;
import game.utils.Utils;
import io.netty.channel.ChannelHandlerContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import byCodeGame.game.tools.CacheLockUtil;

public class RegisterServiceImpl implements RegisterService {
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	private CompanyDao companyDao;

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private EmployeeService employeeService;
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	private EmployeeDao employeeDao;
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public NettyMessage registerRole(ChannelHandlerContext context, String account, String name) {		
		long nowTime = Utils.getCurrentTime();
		NettyMessage message = Message.create();
		Role role = new Role();
		role.setName(name);
		role.setAccount(account);
		role.setProperty(RegisterConstant.INIT_PROPERTY);

		Company company = new Company();

		List<Employee> employeeList = new ArrayList<>();
		for (int i = 0; i < RegisterConstant.INIT_EMPLOYEE_COUNT; i++) {
			Employee employee = new Employee();
			employeeList.add(employee);
			employeeService.initEmployeeAttribute(employee, nowTime);
		}
		
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		
		try (Connection conn = dataSource.getConnection()) {
			if (this.checkRepeatAccount(role.getAccount())) {
				return null;
			}
			
			if(this.checkRepeatName(role.getName())){
				return null;
			}
			
			conn.setAutoCommit(false);

			int roleId = roleDao.insertRoleNotCloseConnection(role, conn);
			role.setId(roleId);

			int companyId = companyDao.insertCompanyNotCloseConnection(company, conn);			
			company.setRoleId(roleId);			
			company.setCompanyId(companyId);

			for(Employee employee:employeeList){
				employee.setCompanyId(companyId);
				employee.setRoleId(roleId);
			}
			
			Map<Employee, Integer> employeeMap = employeeDao.insertEmployeesNotCloseConnection(employeeList, conn);
			
			conn.commit();
			conn.setAutoCommit(true);
			
			ContextCache.bindRoleAndChannel(role, context);

			RoleCache.putRole(role);
			CompanyCache.putCompany(company);
			
			for (Map.Entry<Employee, Integer> entry : employeeMap.entrySet()) {
				Employee employee = entry.getKey();
				int id = entry.getValue();
				employee.setEmployeeId(id);
				EmployeeCache.pushEmployee(employee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
		message.putInt(ErrorCode.SUCCESS);
		
		return message;
	}
	
	private boolean checkRepeatName(String name) {
		if(RoleCache.getAllName().contains(name)){
			return true;
		}
		return false;
	}

	private boolean checkRepeatAccount(String account){
		if(RoleCache.getAllAccount().contains(account)){
			return true;
		}
		return false;
	}
}
