package com.candou.ac.rom.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.candou.ac.rom.clean.CleanIllegalApp;
import com.candou.ac.rom.main.DownloadMain;
import com.candou.ac.rom.main.RomMain;

public class RomJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		RomMain.main(null);
		DownloadMain.main(null);
		CleanIllegalApp.main(null);
	}

}
