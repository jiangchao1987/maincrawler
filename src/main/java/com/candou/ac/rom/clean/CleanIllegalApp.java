package com.candou.ac.rom.clean;

import java.io.File;

import org.apache.log4j.Logger;

import com.candou.ac.rom.dao.RomAppDao;

public class CleanIllegalApp {
    private static final String path = "/data2/android/rom/roms";
    private static Logger logger = Logger.getLogger(CleanIllegalApp.class);

    public static void main(String[] args) {
        File folder = new File(path);
        logger.info("total find " + folder.listFiles().length + " files");
        
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.length() < 10 * 1024 * 1000) {
                logger.info("illegal file [" + file.getName() + "]");
                if (file.delete()) {
                    // 将tb_app的filename更新为null
                    RomAppDao.updateFileName(file.getName());
                }
            }
        }
    }
}
