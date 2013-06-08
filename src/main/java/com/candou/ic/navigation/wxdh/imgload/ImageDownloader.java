package com.candou.ic.navigation.wxdh.imgload;

import java.awt.color.CMMException;
import java.io.File;

import org.apache.log4j.Logger;

import com.candou.util.ImageUtil;


public class ImageDownloader {
    private static Logger log = Logger.getLogger(ImageDownloader.class);

    public static String downloader(String imageUrl, String dbPath, String savePath,String wxId) {
        if (imageUrl == null) {
            return null;
        }
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1,imageUrl.indexOf("?"));

        File dir = new File(savePath.concat(File.separator).concat(wxId));
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File targetFile = new File(dir.getAbsoluteFile().toString().concat(File.separator).concat(fileName));

        try {
            if (!targetFile.exists()) {
                int retry = 0;
                do {
                    //开始下载
                    ImageUtil.remoteImage(imageUrl, targetFile);
                } while (++retry <= 5 && !targetFile.exists());

                if (targetFile.exists()) {
                    log.info("save to " + targetFile);
                } else {
                    log.warn("download failed");
                }
            }
        } catch (CMMException e) {
            log.error("image format error: " + e.getMessage());
            return null;
        }

        return targetFile.toString();
    }



}
