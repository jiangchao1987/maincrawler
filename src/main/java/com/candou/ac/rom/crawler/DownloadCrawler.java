package com.candou.ac.rom.crawler;

import java.util.List;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.dao.DaoFactory;
import com.candou.ac.rom.downloader.RomDownloader;

public class DownloadCrawler {
    
    public void start() {
        List<RomApp> apps = DaoFactory.getRomAppDao().findApps();
        for (RomApp app : apps) {
            String fileName = RomDownloader.downloader(app.getDownloadUrl());
            if (fileName != null) {
                app.setFilename(fileName);
                DaoFactory.getRomAppDao().updateFileName(app);
            }
        }
    }
}
