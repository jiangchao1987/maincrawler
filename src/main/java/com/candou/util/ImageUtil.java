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

public class ImageUtil {

    private static Logger log = Logger.getLogger(ImageUtil.class);

    public static String getFileName(String url) {
        return MD5Util.getStringMD5(url);
    }

    public static String getDirName(String imageUrl) {
        String md5 = MD5Util.getStringMD5(imageUrl);
        return File.separator
                + md5.substring(0, 2).concat(File.separator).concat(md5.substring(2, 4)).concat(File.separator);
    }

    public static void remoteImage(String url, File target) {
        log.info("download [single mode]: " + url);

        try {
            URL request = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) request
                    .openConnection();

            HttpURLConnection.setFollowRedirects(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent",
                    BrowserUtil.getRandomBrowserUserAgent());
            connection.setRequestProperty("Accept-Language",
                    "zh-cn, zh;q=0.75, en-us;q=0.50, en;q=0.25");

            InputStream is = connection.getInputStream();

            if (null == is) {
                log.error("连接对象为空");
                return;
            }
            DataInputStream in = new DataInputStream(is);
            DataOutputStream out = new DataOutputStream(new FileOutputStream(
                    target));
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

}
