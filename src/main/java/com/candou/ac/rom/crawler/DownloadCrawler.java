package com.candou.ac.rom.crawler;

import java.util.List;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.dao.AppDao;
import com.candou.ac.rom.downloader.RomDownloader;

public class DownloadCrawler {
    
    public void start() {
//        List<RomApp> apps = DaoFactory.getRomAppDao().findApps();
    	List<RomApp> apps = AppDao.findApps();
        for (RomApp app : apps) {
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
