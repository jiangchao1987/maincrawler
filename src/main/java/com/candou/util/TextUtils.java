package com.candou.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




/**
 * 文本处理工具类
 *
 * @author wenming
 */
public class TextUtils {

    private static Logger log=Logger.getLogger(TextUtils.class);

    /**
     * 获取远程内容
     *
     * @param url
     * @return
     */
    public static String fetchContent(String url) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    new URL(url).openStream(), "UTF-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据css选择器返回html中的内容 返回单一结果
     *
     * @param html
     * @param query
     * @return
     */
    public static String selectElementHtml(String html, String query) {
        Document doc = Jsoup.parse(html);
        Element element = doc.select(query).first();

        return element.html();
    }

    /**
     * @param html
     * @param query
     * @return
     * @author fanzhiwei
     */
    public static Element selectElement(String html, String query) {
        Document doc = Jsoup.parse(html);
        Element element = doc.select(query).first();
        return element;
    }

    /**
     * 根据css选择器返回html中的内容 返回结果集
     *
     * @param html
     * @param query
     * @return
     */
    public static List<String> selectElementsHtml(String html, String query) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(query);

        List<String> list = new LinkedList<String>();
        for (Element element : elements) {
            list.add(element.html());
        }
        return list;
    }

    /**
     * 根据css选择器返回html中的内容 返回结果集
     * @param html
     * @param query
     * @return
     * @author fanzhiwei
     */
    public static List<Element> selectElements(String html, String query) {
    	 Document doc = Jsoup.parse(html);
         Elements elements = doc.select(query);

         List<Element> list = new LinkedList<Element>();
         for (Element element : elements) {
             list.add(element);
         }
         return list;
    }

    /**
     * 取得待抓取的种子列表
     *
     * @param file
     * @return
     */
    public static Map<String, String> getSeedList(InputStream file) {
        Map<String, String> seeds = new HashMap<String, String>();

        if (file != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] splited = line.split("\\|");
                    if (line.startsWith("#") || splited.length != 2) {
                        continue;
                    }

                    seeds.put(splited[1], splited[0]);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            log.error("cannot open inputstream");
        }

        return seeds;
    }

    public static String encryptedByMD5(String s) {
        String contentEncryptedByMd5 = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                // System.out.println((int)b);
                // 将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            contentEncryptedByMd5 = new String(str);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return contentEncryptedByMd5;
    }

}
