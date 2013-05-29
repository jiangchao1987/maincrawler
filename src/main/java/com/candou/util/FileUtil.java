package com.candou.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.candou.util.thread.DownloadTask;
import com.candou.util.thread.DownloadTaskEvent;
import com.candou.util.thread.DownloadTaskListener;

public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	private static int lastProgress = -1;
	public static boolean finished;

	/**
	 * 单线程下载
	 * 
	 * @param url
	 * @param target
	 */
	public static void remoteFile(String url, File target) {
		url = url.replace(" ", "%20"); 
		
		logger.info("download: " + url);

		try {
			URL request = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) request.openConnection();

			HttpURLConnection.setFollowRedirects(true);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", BrowserUtil.getRandomBrowserUserAgent());
			connection.setRequestProperty("Accept-Language", "zh-cn, zh;q=0.75, en-us;q=0.50, en;q=0.25");

			InputStream is = connection.getInputStream();

			if (null == is) {
				logger.error("连接对象为空");
				return;
			}
			DataInputStream in = new DataInputStream(is);
			DataOutputStream out = new DataOutputStream(new FileOutputStream(target));
			byte[] buffer = new byte[4096];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 多线程下载
	 * 
	 * @param url	文件url
	 * @param target	生成的本地文件
	 * @param debug		是否打开下载调试模式	
	 * @param threadCount	下载线程数
	 */
	public static void remoteFile(String url, File target, boolean debug, int threadCount) {
		url = url.replace(" ", "%20"); 	// 将url中的空格替换成%20, 使用encoder会把空格替换成+, 不符合要求
		
		logger.info("download: " + url);
		DownloadTask.setDebug(debug);
		//target.getAbsolutePath()文件的绝对路径
		DownloadTask task = new DownloadTask(url, target.getAbsolutePath(), threadCount);
		try {
			logger.info("---开始下载---");
			task.startDown();
			logger.info("---监听线程---");
			task.addTaskListener(new DownloadTaskListener() {
				public void autoCallback(DownloadTaskEvent event) {
					int progress = (int) (event.getReceivedCount() * 100.0 / event.getTotalCount());
					if (progress % 5 == 0 && progress != lastProgress) {
						lastProgress = progress;
						logger.info("downloading: " + progress + "%, current speed: " + event.getRealTimeSpeed()
								+ ", global speed: " + event.getGlobalSpeed());
					}
					
					if (progress == 100) {
						finished = true;
					}
				}
			});
		} catch (Exception e) {
			finished = true;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int index = 0; index < 10; index++)  {
			//String url = "http://download.shendu.com/downloads/rom/886-shendu.com-samsung-s5830-cm-7.2.0-cooper-Sip-signed.zip";
			String url = "http://download.shendu.com/downloads/rom/motorola/MB860_ME860/368_shendu_artix4gv1.0.zip";
			File target = new File("F:\\androidrom\\roms\\" + index + ".zip");
			remoteFile(url, target, false, 6);
			
			finished = false;

			while (!finished) {
				Thread.sleep(10 * 1000);
			}
		}
	}

}
