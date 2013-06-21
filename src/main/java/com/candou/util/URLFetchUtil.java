package com.candou.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class URLFetchUtil {
	private static Logger logger = Logger.getLogger(URLFetchUtil.class);

	public static String fetchGet(String url) {
		return fetch(url, "GET");
	}

	public static String fetchPost(String url) {
		return fetch(url, "POST");
	}

	public static String fetch(String url, String method) {
		logger.info("Connection " + url);

		String content = null;
		try {
			URL request = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) request.openConnection();

			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestMethod(method);
			connection.setRequestProperty("User-Agent", BrowserUtil.getRandomBrowserUserAgent());
			connection.setRequestProperty("Accept-Language", "zh-cn, zh;q=0.75, en-us;q=0.50, en;q=0.25");

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = in.readLine()) != null) {
					sb.append(line).append(System.getProperty("line.separator"));
				}
				content = sb.toString();
			} else {
				logger.error(String.format("Response status error: %d - %s", connection.getResponseCode(), connection.getResponseMessage()));
				content = "404 NOT FOUND";
			}
		} catch (IOException e) {
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

	public static String fetchPost(String url, HttpEntity httpEntity, Map<String, String> headers) {
	    String content = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost(url);
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                httpPost.addHeader(key, headers.get(key));
            }
            httpPost.setEntity(httpEntity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            content = EntityUtils.toString(respEntity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return content;
    }

}
