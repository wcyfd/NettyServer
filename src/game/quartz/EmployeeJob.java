package game.quartz;

import game.module.employee.service.EmployeeService;
import game.utils.Utils;
import game.utils.spring.SpringContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @author wcy 2016年3月31日
 *
 */
public class EmployeeJob implements Job {

	private EmployeeService employeeService = null;

	public EmployeeJob() {
		employeeService = SpringContext.getBean("employeeService");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		long nowTime = Utils.getCurrentTime();
		this.employeeService.createEmployee(20, nowTime);
	}

}
