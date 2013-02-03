/**
 * 正版应用(iphone): http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=soft&orderby=publishDate&catid=0&page=1
 * 正版游戏(iphone): http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=game&orderby=publishDate&catid=0&page=1
 * 正版应用(ipad): http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=soft&orderby=publishDate&catid=0&page=1
 * 正版游戏(ipad): http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=game&orderby=publishDate&catid=0&page=1 
 * 
 */
package com.candou.ic.market.pp.main;

import com.candou.ic.market.pp.crawler.PPCrawler;

public class PPMain {

	private static String[] all = {
			"http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=soft&orderby=publishDate&catid=0&page=%d",
			"http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=game&orderby=publishDate&catid=0&page=%d" };

	public static void main(String[] args) {
		new PPCrawler().start(all);
	}

}
