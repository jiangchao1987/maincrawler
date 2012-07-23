package com.candou.ac.rom.clean;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.dao.RomAppDao;
import com.candou.conf.Configure;

/**
 * 验证应用实际大小与网站记录的差值是否小于5M
 */
public class CleanIllegalApp {
//    private static final String path = "/data2/android/rom/roms";
//	private static final String BASE_PATH = "C:/androidrom";
	private static final String BASE_PATH = Configure.getProperty("rom_basepath");
    private static Logger logger = Logger.getLogger(CleanIllegalApp.class);

    public static void main(String[] args) {
        /*File folder = new File(path);
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
        }*/
    	
    	List<RomApp> apps = findApps();
    	for (RomApp app : apps) {
    		if (!isValid(app)) {
    			// delete && update filename to null
    			File illegalRom = new File(BASE_PATH + app.getFileName());
    			if (illegalRom.exists() && illegalRom.delete()) {
    				RomAppDao.updateFileName(app.getFileName());
    			}
    		}
    	}
    }
    
    private static List<RomApp> findApps() {
    	List<RomApp> apps = RomAppDao.findAvailableApps();
    	return apps;
    }
    
    private static boolean isValid(RomApp app) {
    	boolean result = false;
    	String path = BASE_PATH + app.getFileName();
    	
    	File rom = new File(path);
    	if (rom.exists()) {
    		float minus = Math.abs(rom.length() / (1024 * 1024) - app.getSize());
    		
    		logger.info("file: " + rom.length() / (1024 * 1024) + " db: " + app.getSize());
    		
    		if (minus < 5) {
    			result = true;
    		}
    	}
    	
    	return result;
    }
    
}
