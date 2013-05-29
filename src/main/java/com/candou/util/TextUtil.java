package com.candou.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 文本处理工具类
 *
 * @author wenming
 */
public class TextUtil {

	/**
	 * 取得待抓取的种子列表
	 *
	 * @param fileName
	 * @return
	 */
	public static Map<String, String> getSeedList(InputStream io) {
		Map<String, String> seeds = new HashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(io));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] splited = line.split("\\|");
				if (!line.startsWith("#") && splited.length == 2) {
					seeds.put(splited[0], splited[1]);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return seeds;
	}
}
