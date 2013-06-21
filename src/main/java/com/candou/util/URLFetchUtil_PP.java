package com.candou.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

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
 * ppLimited限免
 *baseUrl  :  http://jsondata.25pp.com/index.html
 *
 *tunnel-command:4261425184  
 *苹果官方商店 限免(iphone)：  {"dcType":1,"resType":10,"listType":0,"perCount":18,"page":0}  9
 *苹果官方商店 限免(ipad)：      {"dcType":2,"resType":10,"listType":0,"perCount":18,"page":0}  10
 * */
public class URLFetchUtil_PP {
	private static Logger logger = Logger.getLogger(URLFetchUtil_PP.class);

	public static void main(String[] args) {
		fetch(1,0);
	}

	// 只传过来类型type 在方法中判断
	public static String fetch(int type, int pn) {
		String url = "http://jsondata.25pp.com/index.html";
		logger.info("Connection " + url);
		String content = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			// 添加http头信息
				switch (type) {
				case 1:
					httppost.addHeader("tunnel-command", "4261425200");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
									+ pn + "}"));
					break;
				case 2:
					httppost.addHeader("tunnel-command", "4261425200");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":2,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
									+ pn + "}"));
					break;
				case 3:
					httppost.addHeader("tunnel-command", "4261425200");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":1,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
									+ pn + "}"));
					break;
				case 4:
					httppost.addHeader("tunnel-command", "4261425200");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":2,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
									+ pn + "}"));
					break;
				case 5:
					httppost.addHeader("tunnel-command", "4261425184");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
									+ pn + "}"));
					break;
				case 6:
					httppost.addHeader("tunnel-command", "4261425184");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":2,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
									+ pn + "}"));
					break;
				case 7:
					httppost.addHeader("tunnel-command", "4261425184");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":1,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
									+ pn + "}"));
					break;
				case 8:
					httppost.addHeader("tunnel-command", "4261425184");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":2,\"resType\":2,\"listType\":0,\"catId\":0,\"perCount\":12,\"page\":"
									+ pn + "}"));
					break;
				case 9:
					httppost.addHeader("tunnel-command", "4261425184");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":1,\"resType\":10,\"listType\":0,\"catId\":0,\"perCount\":18,\"page\":"
									+ pn + "}"));
					break;
				case 10:
					httppost.addHeader("tunnel-command", "4261425184");
					httppost.setEntity(new StringEntity(
							"{\"dcType\":2,\"resType\":10,\"listType\":0,\"catId\":0,\"perCount\":18,\"page\":"
									+ pn + "}"));
					break;
				default:
					logger.error("未知类型!参数错误!");
					break;
				}

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				content = EntityUtils.toString(entity);
				System.out.println(response.getStatusLine());
				System.out.println(content);
			} else {
				logger.error(String.format("Response status error: %d - %s",response.getStatusLine().getStatusCode()));
				content = "404 NOT FOUND";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: " + e.getMessage());

			try {
				logger.info("sleep 30s ...");
				Thread.sleep(30000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return content;
	}
	/*
	 * public static void main(String[] args) throws IOException { HttpClient
	 * httpclient = new DefaultHttpClient(); try { HttpPost httpPost = new
	 * HttpPost("http://jsondata.25pp.com/index.html");
	 * httpPost.addHeader("tunnel-command", "4261425200");
	 * httpPost.setEntity(new StringEntity(
	 * "{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":0}"
	 * )); HttpResponse response = httpclient.execute(httpPost); HttpEntity
	 * entity = response.getEntity();
	 * System.out.println(String.format("status code: %d, content: %s",
	 * response.getStatusLine(), EntityUtils.toString(entity))); } finally {
	 * httpclient.getConnectionManager().shutdown(); } }
	 */

	/*
	 * public static void main(String[] args) throws ClientProtocolException,
	 * IOException { String url = "http://jsondata.25pp.com/index.html";
	 * HttpClient client = new DefaultHttpClient(); HttpPost post = new
	 * HttpPost(url); post.addHeader("tunnel-command", "4261425200");
	 * post.setEntity(new StringEntity(
	 * "{\"dcType\":2,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":0}"
	 * )); HttpResponse response = client.execute(post); HttpEntity entity =
	 * response.getEntity(); System.out.println(response.getStatusLine());
	 * System.out.println(EntityUtils.toString(entity)); }
	 */

}
