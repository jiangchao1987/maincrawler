/**
 * 正版应用(iphone): http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=soft&orderby=publishDate&catid=0&page=1
 * 正版游戏(iphone): http://pppc.25pp.com/appleshare/app?son_phone_typeid=1&a=game&orderby=publishDate&catid=0&page=1
 * 正版应用(ipad): http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=soft&orderby=publishDate&catid=0&page=1
 * 正版游戏(ipad): http://pppc.25pp.com/appleshare/app?son_phone_typeid=2&a=game&orderby=publishDate&catid=0&page=1 
 * 上面的已经不能用了.
 */

/**
 * baseUrl  :  http://jsondata.25pp.com/index.html
 * tunnel_command : 4261425200
 * 
 *正版应用-应用(iphone)   {"dcType":1,"resType":1,"listType":0,"catId":0,"perCount":15,"page":0}
 *正版应用-应用ipad       {"dcType":2,"resType":1,"listType":0,"catId":0,"perCount":15,"page":0}
 *正版应用-游戏iphone     {"dcType":1,"resType":2,"listType":0,"catId":0,"perCount":15,"page":0}
 *正版应用-游戏ipad       {"dcType":2,"resType":2,"listType":0,"catId":0,"perCount":15,"page":0}
 * */
package com.candou.ic.market.pp.main;

import com.candou.ic.market.pp.crawler.PPCrawler;

public class PPMain {

	public static void main(String[] args) {
		int [] jobs =new int[]{1,2,3,4};
		for (int i = 0; i < jobs.length; i++) {
			new PPCrawler().start(jobs[i]);
		}
	}

}
