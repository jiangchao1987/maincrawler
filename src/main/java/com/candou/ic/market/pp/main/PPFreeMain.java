/**
 * 苹果官方免费软件(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=softFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费游戏(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=gameFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费软件(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=softFree&orderby=publishDate&catid=0&page=1
 * 苹果官方免费游戏(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=gameFree&orderby=publishDate&catid=0&page=1
 * 上面的已经不能用了.
 */

/**
 *baseUrl  :  http://jsondata.25pp.com/index.html
 *
 *tunnel-command:4261425184  
 *苹果官方商店免费软件(iphone)：  {"dcType":1,"resType":1,"listType":0,"catId":0,"perCount":12,"page":0}
 *苹果官方商店免费软件(ipad)：      {"dcType":2,"resType":1,"listType":0,"catId":0,"perCount":12,"page":0}
 *苹果官方商店免费游戏(iphone)：  {"dcType":1,"resType":2,"listType":0,"catId":0,"perCount":12,"page":0}
 *苹果官方商店免费游戏(ipad)：      {"dcType":2,"resType":2,"listType":0,"catId":0,"perCount":12,"page":0}
 * */
package com.candou.ic.market.pp.main;

import com.candou.ic.market.pp.crawler.FreeCrawler;

public class PPFreeMain {


	public static void main(String[] args) {
		int [] jobs =new int[]{5,6,7,8};
		for (int i = 0; i < jobs.length; i++) {
			new FreeCrawler().start(jobs[i]);
		}
	}

}
