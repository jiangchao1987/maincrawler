package com.candou.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置文件类
 *
 * 负责从配置文件中取得对应设置
 *
 * @author tangwenming
 *
 */
public class Configure {
	private static Properties property = new Properties();
	private static Logger logger = Logger.getLogger(Configure.class);

	static {
		try {
			property.load(new FileInputStream(System.getProperty("user.dir") + "/conf/configure.properties"));
		} catch (IOException e) {
			logger.error(e.getMessage());
			System.exit(-1);
		}
	}

	public static String getProperty(String key) {
		return property.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		return property.getProperty(key, defaultValue);
	}
}
