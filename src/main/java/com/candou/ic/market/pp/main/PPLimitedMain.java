/**
 * 苹果官方限免(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=timeFree&orderby=freeTime&catid=0&page=1
 * 苹果官方限免(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=timeFree&orderby=freeTime&catid=0&page=1
 */
package com.candou.ic.market.pp.main;

import com.candou.ic.market.pp.crawler.LimitedCrawler;

public class PPLimitedMain {

	private static String[] limited = {"http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=timeFree&orderby=freeTime&catid=0&page=%d"};

	public static void main(String[] args) {
		new LimitedCrawler().start(limited);
	}
	
}
