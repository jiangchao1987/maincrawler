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
    	
    	//获取应用ID和下载URL
    	List<RomApp> apps = AppDao.findApps();
    	int i= 0;
        for (RomApp app : apps) {
        	log.info("正在处理第:"+i+1+"个。");
        	log.info(String.format("dowloading: appId[%d], appName[%s], downloadUrl[%s]", app.getAppId(), app.getAppName(), app.getDownloadUrl()));
        	log.info("开始下载文件");
            String fileName = RomDownloader.downloader(app.getDownloadUrl());
            
            log.info("fileName:------"+fileName);
            if (fileName != null) {
                app.setFilename(fileName);
                app.setFilemd5(RomDownloader.filemd5(app.getDownloadUrl()));
//                DaoFactory.getRomAppDao().updateFileName(app);
                
                log.info(" 更新app应用");
                AppDao.updateFileName(app);
                
            }
        }
    }
    
}
