package game.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {
	public static void openQuartz(){
		SchedulerFactory schedulerfactory = new StdSchedulerFactory();
		try {
			Scheduler scheduler = schedulerfactory.getScheduler();

			JobDetail employeeJob=JobBuilder.newJob(EmployeeJob.class)
					.withIdentity("employeeJob", "jgroup").build();
			Trigger employeeTrigger=TriggerBuilder.newTrigger()
					.withIdentity("employeeTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 * * ?"))
					.startNow().build(); 
			scheduler.scheduleJob(employeeJob, employeeTrigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
