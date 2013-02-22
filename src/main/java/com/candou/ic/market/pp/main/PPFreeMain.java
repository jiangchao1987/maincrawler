/**
 * 苹果官方免费软件(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=softFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费游戏(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=gameFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费软件(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=softFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费游戏(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=gameFree&orderby=publishDate&catid=0&page=1
 */
package com.candou.ic.market.pp.main;

import com.candou.ic.market.pp.crawler.FreeCrawler;

public class PPFreeMain {

	private static String[] free = {
			"http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=softFree&orderby=publishDate&catid=0&page=%d",
			"http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=gameFree&orderby=publishDate&catid=0&page=%d" };

	public static void main(String[] args) {
		new FreeCrawler().start(free);
	}

}
