package com.candou.ic.market.pp.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.candou.util.URLFetchUtil;

/**
 * ppMain
 * baseUrl  :  http://jsondata.25pp.com/index.html
 * tunnel_command : 4261425200
 * 
 *正版应用-应用(iphone)   {"dcType":1,"resType":1,"listType":0,"catId":0,"perCount":15,"page":0}       1
 *正版应用-应用ipad       {"dcType":2,"resType":1,"listType":0,"catId":0,"perCount":15,"page":0}       2 
 *正版应用-游戏iphone     {"dcType":1,"resType":2,"listType":0,"catId":0,"perCount":15,"page":0}       3
 *正版应用-游戏ipad       {"dcType":2,"resType":2,"listType":0,"catId":0,"perCount":15,"page":0}       4
 * */

/**
 * ppFree免费
 * baseUrl : http://jsondata.25pp.com/index.html
 * 
 * tunnel-command:4261425184 
 * 苹果官方商店免费软件(iphone)：
 * {"dcType":1,"resType":1,"listType":0,"catId":0,"perCount":12,"page":0}  5
 * 苹果官方商店免费软件(ipad)：
 * {"dcType":2,"resType":1,"listType":0,"catId":0,"perCount":12,"page":0}  6
 * 苹果官方商店免费游戏(iphone)：
 * {"dcType":1,"resType":2,"listType":0,"catId":0,"perCount":12,"page":0}  7
 * 苹果官方商店免费游戏(ipad)：
 * {"dcType":2,"resType":2,"listType":0,"catId":0,"perCount":12,"page":0}  8
 * */

/**
 * ppLimited限免 baseUrl : http://jsondata.25pp.com/index.html
 * 
 * tunnel-command:4261425184 苹果官方商店 限免(iphone)：
 * {"dcType":1,"resType":10,"listType":0,"perCount":18,"page":0} 9 苹果官方商店
 * 限免(ipad)： {"dcType":2,"resType":10,"listType":0,"perCount":18,"page":0} 10
 * */
public class NotificationCenter {
	private static Logger logger = Logger.getLogger(NotificationCenter.class);
	public static final String BASE_URL = "http://jsondata.25pp.com/index.html";
	public static final String TUNNEL_COMMAND1 = "4261425200";
	public static final String TUNNEL_COMMAND2 = "4261425184";

	public static String notify(int type, int pn) {
		String content = null;
		// map<key,value>
		// map中自动按key排序，而且key不可以重复，只允许一个key存在，后来的相同key会自动覆盖之前的value；
		Map<String, String> headers = new HashMap<String, String>();
		StringEntity httpEntity;
		try {
			switch (type) {
			case 1:
				headers.put("tunnel-command", TUNNEL_COMMAND1);
				httpEntity = new StringEntity(
						"{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 2:
				headers.put("tunnel-command", TUNNEL_COMMAND1);
				httpEntity = new StringEntity(
						"{\"dcType\":2,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 3:
				headers.put("tunnel-command", TUNNEL_COMMAND1);
				httpEntity = new StringEntity(
						"{\"dcType\":1,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 4:
				headers.put("tunnel-command", TUNNEL_COMMAND1);
				httpEntity = new StringEntity(
						"{\"dcType\":2,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 5:
				headers.put("tunnel-command", TUNNEL_COMMAND2);
				httpEntity = new StringEntity(
						"{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 6:
				headers.put("tunnel-command",TUNNEL_COMMAND2);
				httpEntity = new StringEntity(
						"{\"dcType\":2,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 7:
				headers.put("tunnel-command", TUNNEL_COMMAND2);
				httpEntity = new StringEntity(
						"{\"dcType\":1,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 8:
				headers.put("tunnel-command", TUNNEL_COMMAND2);
				httpEntity = new StringEntity(
						"{\"dcType\":2,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 9:
				headers.put("tunnel-command", TUNNEL_COMMAND2);
				httpEntity = new StringEntity(
						"{\"dcType\":1,\"resType\":10,\"listType\":0,\"catId\":0,\"perCount\":18,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			case 10:
				headers.put("tunnel-command", TUNNEL_COMMAND2);
				httpEntity = new StringEntity(
						"{\"dcType\":2,\"resType\":10,\"listType\":0,\"catId\":0,\"perCount\":18,\"page\":"
								+ pn + "}");
				content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
				break;
			default:
				logger.error("未知类型!参数错误!");
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: " + e.getMessage());
		}
		return content;
	}

}
