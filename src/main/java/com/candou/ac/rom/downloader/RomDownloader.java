package com.candou.ac.rom.downloader;

import java.io.File;

import org.apache.log4j.Logger;

import com.candou.conf.Configure;
import com.candou.util.FileUtil;

public class RomDownloader {
	private static Logger log = Logger.getLogger(RomDownloader.class);

	public static String downloader(String url) {
		String fileName = getFileName(url);
		File dir = new File(Configure.getProperty("rom_path"), getSaveDir());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File targetFile = new File(dir, fileName);
		if (targetFile.exists()) {
			targetFile.delete();
		}

		int retry = 0;
		do {
			boolean downloadDebug = false;
			if (Configure.getProperty("download_debug_mode").equalsIgnoreCase("yes")) {
				downloadDebug = true;
			}
			
			FileUtil.finished = false;
			
			FileUtil.remoteFile(url, targetFile, downloadDebug,
					Integer.parseInt(Configure.getProperty("max_thread_count")));

			while (!FileUtil.finished) {
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} while (++retry <= 5 && !targetFile.exists());

		if (targetFile.exists()) {
			log.info("save to " + targetFile);
		} else {
			log.warn("download failed");
			return null;
		}

		return Configure.getProperty("rom_url").concat(getSaveDir() + fileName);
	}

	private static String getSaveDir() {
		return "/";
	}

	private static String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.length()).replace("shendu", "candou")
				.replace("ShenDu", "candou");
	}

}
