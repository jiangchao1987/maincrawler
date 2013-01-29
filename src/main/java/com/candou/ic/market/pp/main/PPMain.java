/**
 * 修改db_name = db_application
 * 正版应用(iphone): http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=soft&orderby=publishDate&catid=0&page=1
 * 正版游戏(iphone): http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=game&orderby=publishDate&catid=0&page=1
 * 修改db_name = db_ipad
 * 正版应用(ipad): http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=soft&orderby=publishDate&catid=0&page=1
 * 正版游戏(ipad): http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=game&orderby=publishDate&catid=0&page=1 
 * 
 * 苹果官方免费软件(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=softFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费软件(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=softFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费游戏(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=gameFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费游戏(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=gameFree&orderby=publishDate&catid=0&page=1
 * 苹果官方限免(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=timeFree&orderby=freeTime&catid=0&page=1
 * 苹果官方限免(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=timeFree&orderby=freeTime&catid=0&page=1
 */
package com.candou.ic.market.pp.main;

import com.candou.ic.market.pp.crawler.PPCrawler;

public class PPMain {

	private static String[] sources = {
			"http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=soft&orderby=publishDate&catid=0&page=%d",
			"http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=game&orderby=publishDate&catid=0&page=%d",
			"http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=softFree&orderby=publishDate&catid=0&page=%d",
			"http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=gameFree&orderby=publishDate&catid=0&page=%d" };

	public static void main(String[] args) {
		new PPCrawler().start(sources);
	}

}
