package com.candou.ic.navigation.jcwx.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.ic.navigation.jcwx.bean.App;
import com.candou.ic.navigation.jcwx.bean.Job;
import com.candou.ic.navigation.jcwx.bean.Like;
import com.candou.ic.navigation.jcwx.crawler.parser.JCWX_AppParser;
import com.candou.ic.navigation.jcwx.dao.JobDao;

public class JCWX_AppCrawler {

    private static Logger log = Logger.getLogger(JCWX_AppCrawler.class);
    private static int batchAddLimit = 20;
    private static int batchAddPhotoLimit = 10;

    public void start() {
        List<Job> jobs = JobDao.findJobs();
        List<App> apps = new ArrayList<App>();
        List<Like> photos = new ArrayList<Like>();
        List<Job> fetchedJobs = new ArrayList<Job>();
        List<Job> failedJobs = new ArrayList<Job>();

        int appcounter = 0;
        int all = jobs.size();
        for (Job job : jobs) {
            log.info("[" + ++appcounter + "/" + all + "] " + job.getTitle());

            App app = JCWX_AppParser.parse(job);
        }
    }
}
