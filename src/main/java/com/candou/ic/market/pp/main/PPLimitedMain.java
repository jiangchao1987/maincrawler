/**
 * 苹果官方限免(iphone)：http://pppc.25pp.com/apple/soft?son_phone_typeid=1&a=timeFree&orderby=freeTime&catid=0&page=1
 * 苹果官方限免(ipad)：http://pppc.25pp.com/apple/soft?son_phone_typeid=2&a=timeFree&orderby=freeTime&catid=0&page=1
 * 上面的已经不能用了.
 */

/**
 *baseUrl  :  http://jsondata.25pp.com/index.html
 *
 *tunnel-command:4261425184  
 *苹果官方商店 限免(iphone)：  {"dcType":1,"resType":10,"listType":0,"perCount":18,"page":0}
 *苹果官方商店 限免(ipad)：      {"dcType":2,"resType":10,"listType":0,"perCount":18,"page":0}
 * */
package com.candou.ic.market.pp.main;

import com.candou.ic.market.pp.crawler.LimitedCrawler;

public class PPLimitedMain {


	public static void main(String[] args) {
		int [] jobs =new int[]{9,10};
		for (int i = 0; i < jobs.length; i++) {
			new LimitedCrawler().start(jobs[i]);
		}
	}
	
}
