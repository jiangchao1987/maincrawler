package com.candou.ac.rom.clean;

import java.io.File;
import java.util.List;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.dao.AppDao;
import com.candou.ac.rom.dao.DaoFactory;
import com.candou.conf.Configure;

/**
 * 重命名下载的rom。 
 */
public class RenameIllegalApp {
	private static final String BASE_PATH = Configure.getProperty("rom_basepath");
//	private static final String BASE_PATH = "C:/androidrom";

	public static void main(String[] args) {
		/*File oldFile = new File("f:/pokr-b201207091437-jar-with-dependencies.jar");
		File newFile = new File("f:/pokr.jar");
		if (oldFile.exists()) {
			oldFile.renameTo(newFile);
		}*/
		
		List<RomApp> apps = findApps();
    	for (RomApp app : apps) {
    		String filename = app.getFilename();
    		if (filename.toLowerCase().contains("shendu")) {
    			// rename file
    			if (renameFile(filename)) {
    				String newFilename = filename.replace("shendu", "candou").replace("ShenDu", "candou");
    				app.setFilename(newFilename);
//    				DaoFactory.getRomAppDao().updateFileName(app);
    				AppDao.updateFileName(app);
    			}
    		}
    	}
	}
	
	private static boolean renameFile(String path) {
		File oldFile = new File(BASE_PATH + path);
		
		String newPath = path.replace("shendu", "candou");
		
		File newFile = new File(BASE_PATH + newPath);
		if (oldFile.exists()) {
			return oldFile.renameTo(newFile);
		}
		
		return false;
	}
	
	private static List<RomApp> findApps() {
//    	List<RomApp> apps = DaoFactory.getRomAppDao().findAvailableApps();
    	List<RomApp> apps = AppDao.findAvailableApps();
		return apps;
    }

}
