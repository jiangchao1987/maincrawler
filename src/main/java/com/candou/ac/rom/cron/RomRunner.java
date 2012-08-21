package com.candou.ac.rom.cron;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.candou.conf.Configure;

public class RomRunner {
	private static Logger log = Logger.getLogger(RomRunner.class);

	public static void main(String[] args) {
		RomRunner runner = new RomRunner();
		runner.run();
	}

	public void run() {
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler sched = sf.getScheduler();
			JobDetail job = newJob(RomJob.class).withIdentity("job-rank", "group-rom").build();
			CronTrigger trigger = newTrigger().withIdentity("trigger-rank", "group-rom").withSchedule(cronSchedule(Configure.getProperty("cron_rom"))).build();

			Date ft = sched.scheduleJob(job, trigger);
			sched.start();
			log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
