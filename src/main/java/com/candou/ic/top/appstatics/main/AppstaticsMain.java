package com.candou.ic.top.appstatics.main;

import com.candou.ic.top.appstatics.crawler.AppstaticsCrawler;

public class AppstaticsMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AppstaticsCrawler crawler = new AppstaticsCrawler();
		crawler.start();
	}

}
