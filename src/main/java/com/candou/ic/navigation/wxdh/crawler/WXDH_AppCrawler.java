package com.candou.ic.navigation.wxdh.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.conf.Configure;
import com.candou.ic.navigation.wxdh.crawler.parser.WXDH_AppParser;
import com.candou.ic.navigation.wxdh.dao.AppDao;
import com.candou.ic.navigation.wxdh.dao.JobDao;
import com.candou.ic.navigation.wxdh.dao.PhotoDao;
import com.candou.ic.navigation.wxdh.imgload.ImageDownloader;
import com.candou.ic.navigation.wxdh.vo.App;
import com.candou.ic.navigation.wxdh.vo.Job;
import com.candou.ic.navigation.wxdh.vo.Photo;

public class WXDH_AppCrawler {
    private static Logger log = Logger.getLogger(WXDH_AppCrawler.class);
    private static int batchAddLimit = 20;
    private static int batchAddPhotoLimit = 10;

    public void start() {
        List<Job> jobs = JobDao.findJobs();
        List<App> apps = new ArrayList<App>();
        List<Photo> photos = new ArrayList<Photo>();

        List<Job> fetchedJobs = new ArrayList<Job>();
        List<Job> failedJobs = new ArrayList<Job>();

        int appcounter = 0;
        int all = jobs.size();
        log.info(jobs.size());
        for (Job job : jobs) {
            log.info("[" + ++appcounter + "/" + all + "] " + job.getName());
            App app = WXDH_AppParser.parse(job);

            if (app == null) {
                failedJobs.add(job);
                continue;
            }
            // image save to local BEGIN
            List<Photo> localPhotos = photoDownloader(app.getPhotos());
            app.setPhotos(localPhotos);
            // icon save to local BEGIN
            String localIconUrl = ImageDownloader.downloader(app.getIcon(), Configure.getProperty("icon_db_path"),
                Configure.getProperty("icon_save_path"));
            app.setIcon(localIconUrl);
            // imc save to local BEGIN 二维码
            String localImcUrl = ImageDownloader.downloader(app.getImc(), Configure.getProperty("imc_db_path"),
                Configure.getProperty("imc_save_path"));
            app.setImc(localImcUrl);
            apps.add(app);

            fetchedJobs.add(job);

            // app入库
            if (apps.size() >= batchAddLimit || appcounter >= all) {
                AppDao.addBatchApps(apps);
                apps.clear();
                JobDao.batchUpdateMatchedStatus(fetchedJobs);
                fetchedJobs.clear();
                log.info("batch add apps");
            }

            photos.addAll(app.getPhotos());
            // photo入库
            if (photos.size() >= batchAddPhotoLimit || appcounter >= all) {
                PhotoDao.addBatchPhotos(photos);
                photos.clear();
                log.info("batch add Photos");
            }

            // update failed jobs
            JobDao.batchUpdateFailedStatus(failedJobs);
            log.info("faild fetch job:" + failedJobs);
        }
    }

    public static List<Photo> photoDownloader(List<Photo> remotePhotos) {
        List<Photo> localPhotos = new ArrayList<Photo>();

        for (Photo photo : remotePhotos) {
            String localThumbnail = ImageDownloader.downloader(photo.getPhoto_url(), Configure.getProperty("photo_db_path"),
                Configure.getProperty("photo_save_path"));

            Photo newPhoto = new Photo();
            newPhoto.setAppid(photo.getAppid());
            newPhoto.setId(photo.getId());
            newPhoto.setPhoto_url(localThumbnail);
            newPhoto.setCreated_at(photo.getCreated_at());
            newPhoto.setUpdated_at(photo.getUpdated_at());

            localPhotos.add(newPhoto);
        }

        return localPhotos;
    }

}
