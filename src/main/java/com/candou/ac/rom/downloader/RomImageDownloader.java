package com.candou.ac.rom.downloader;

import java.io.File;

import org.apache.log4j.Logger;

import com.candou.conf.Configure;
import com.candou.util.FileUtil;

public class RomImageDownloader {
    private static Logger log = Logger.getLogger(RomImageDownloader.class);

    public static String downloader(String url) {
        String fileName = getFileName(url);
        File dir = new File(Configure.getProperty("rom_imagepath"), getSaveDir(url, fileName));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File targetFile = new File(dir, fileName);
        if (!targetFile.exists()) {
            int retry = 0;
            do {
                FileUtil.remoteFile(url, targetFile);
            } while (++retry <= 5 && !targetFile.exists());

            if (targetFile.exists()) {
                log.info("save to " + targetFile);
            } else {
                log.warn("download failed");
            }
        }
        return Configure.getProperty("rom_imageurl").concat(getSaveDir(url, fileName) + fileName);
    }

    private static String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

    private static String getSaveDir(String url, String fileName) {
    	url = url.replace("http://sr.61658.com", "");
        url = url.replace("http://www.shendu.com", "");
        url = url.replace(fileName, "");
        return url;
    }

}
