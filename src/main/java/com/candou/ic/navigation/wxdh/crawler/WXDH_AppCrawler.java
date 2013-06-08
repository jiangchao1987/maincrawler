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

            // 高分辨率 ,低分辨率 save to local BEGIN
            photos = photoDownloader_thumb_screen(app.getPhotos(), app.getWx_id());

            // icon save to local BEGIN 头像
            String localIconUrl = ImageDownloader.downloader(app.getWx_icon_url(), Configure.getProperty("icon_db_path"),
                Configure.getProperty("icon_save_path"), app.getWx_id());
            app.setWx_icon(localIconUrl);
            // imc save to local BEGIN 二维码
            String localImcUrl = ImageDownloader.downloader(app.getWx_qrcode_url(), Configure.getProperty("imc_db_path"),
                Configure.getProperty("imc_save_path"), app.getWx_id());
            app.setWx_qrcode(localImcUrl);

            apps.add(app);

            fetchedJobs.add(job);

            // app入库
            if (apps.size() >= batchAddLimit || appcounter >= all) {
                if (AppDao.exists(app.getWx_displayname())) {
                    log.info("ALREADY EXISTS!");
                    continue;
                }
                AppDao.addBatchApps(apps);
                apps.clear();
                JobDao.batchUpdateMatchedStatus(fetchedJobs);
                fetchedJobs.clear();
                log.info("batch add apps");
            }

            // photo入库
            if (photos.size() > 0) {
                PhotoDao.addBatchPhotos(photos);
                photos.clear();
                log.info("batch add Photos");
            }

            // update failed jobs
            JobDao.batchUpdateFailedStatus(failedJobs);
            log.info("faild fetch job:" + failedJobs);
        }
    }

    /**
     * 抓取低分辨率图片
     *
     * @param remotePhotos
     * @return
     */
    public static List<Photo> photoDownloader_thumb_screen(List<Photo> remotePhotos, String wxId) {
        List<Photo> localPhotos = new ArrayList<Photo>();

        for (Photo photo : remotePhotos) {
            String localThumbnail_thumb = ImageDownloader.downloader(photo.getWx_thumb_url(),
                Configure.getProperty("thumb_db_path"), Configure.getProperty("thumb_save_path"), wxId);

            String localThumbnail_screen = ImageDownloader.downloader(photo.getWx_screen_url(),
                Configure.getProperty("screen_db_path"), Configure.getProperty("screen_save_path"), wxId);

            Photo newPhoto = new Photo();
            newPhoto.setWx_id(photo.getWx_id());
            newPhoto.setWx_photo_id(photo.getWx_photo_id());
            newPhoto.setWx_thumb(localThumbnail_thumb);
            newPhoto.setWx_screen(localThumbnail_screen);

            log.info(newPhoto);

            localPhotos.add(newPhoto);
        }

        return localPhotos;
    }

}
