package com.candou.ac.rom.clean;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.dao.AppDao;
import com.candou.ac.rom.dao.DaoFactory;
import com.candou.conf.Configure;

/**
 * 验证应用实际大小与网站记录的差值是否小于5M
 */
public class CleanIllegalApp {
	private static final String BASE_PATH = Configure.getProperty("rom_basepath");
    private static Logger logger = Logger.getLogger(CleanIllegalApp.class);

    public static void main(String[] args) {
    	List<RomApp> apps = findApps();
    	for (RomApp app : apps) {
    		if (app.getSize() != 0.0f && !isValid(app)) {	//如果size为0说明这个字段没有抓取到
    			// delete && update filename to null
    			File illegalRom = new File(BASE_PATH + app.getFilename());
    			if (illegalRom.exists() && illegalRom.delete()) {
//    				DaoFactory.getRomAppDao().updateFileName(app.getFilename());
    				AppDao.updateFileName(app.getFilename());
    			}
    		}
    	}
    }
    
    private static List<RomApp> findApps() {
//    	List<RomApp> apps = DaoFactory.getRomAppDao().findAvailableApps();
    	List<RomApp> apps = AppDao.findAvailableApps();
    	return apps;
    }
    
    private static boolean isValid(RomApp app) {
    	boolean result = false;
    	String path = BASE_PATH + app.getFilename();
    	
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
