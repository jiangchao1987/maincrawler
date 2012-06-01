package com.candou.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DateTimeUtil {
	public static String nowDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static String getExpireDate() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long now = date.getTime();

		Random random = new Random();
		int hour = random.nextInt(20);

		date.setTime(now + 1000 * 60 * 60 * hour);
		return sd.format(date);
	}

	public static String today() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static String yesterday() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
        long now = date.getTime();
        date.setTime(now - 1000 * 60 * 60 * 24);
        return sdf.format(date);
	}
	
	public static String todayCN() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
	}

	public static int unixtime() {
		return (int) (System.currentTimeMillis() / 1000L);
	}
}
