package com.candou.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * get seeds
 *
 * @author wenming
 *
 */
public class SeedUtil {
	public static Logger log = Logger.getLogger(SeedUtil.class);

	public static InputStream getSeedFile(String file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			System.exit(1);
		}
		return null;
	}
}
