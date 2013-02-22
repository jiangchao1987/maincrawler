package com.candou.ac.rom.crawler;

import java.util.List;

import org.apache.log4j.Logger;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.dao.AppDao;
import com.candou.ac.rom.downloader.RomDownloader;

public class DownloadCrawler {
	private static Logger log = Logger.getLogger(RomDownloader.class);
    
    public void start() {
//        List<RomApp> apps = DaoFactory.getRomAppDao().findApps();
    	List<RomApp> apps = AppDao.findApps();
        for (RomApp app : apps) {
        	log.info(String.format("dowloading: appId[%d], appName[%s], downloadUrl[%s]", app.getAppId(), app.getAppName(), app.getDownloadUrl()));
            String fileName = RomDownloader.downloader(app.getDownloadUrl());
            if (fileName != null) {
                app.setFilename(fileName);
                app.setFilemd5(RomDownloader.filemd5(app.getDownloadUrl()));
//                DaoFactory.getRomAppDao().updateFileName(app);
                AppDao.updateFileName(app);
            }
        }
    }
    
}
