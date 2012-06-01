package com.candou.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class);

    public static void remoteFile(String url, File target) {
        logger.info("download: " + url);

        try {
            URL request = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) request.openConnection();

            HttpURLConnection.setFollowRedirects(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", BrowserUtil.getRandomBrowserUserAgent());
            connection.setRequestProperty("Accept-Language", "zh-cn, zh;q=0.75, en-us;q=0.50, en;q=0.25");

            InputStream is = connection.getInputStream();

            if (null == is) {
                logger.error("连接对象为空");
                return;
            }
            DataInputStream in = new DataInputStream(is);
            DataOutputStream out = new DataOutputStream(new FileOutputStream(target));
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
