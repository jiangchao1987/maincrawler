package com.candou.ic.navigation.wxdh.crawler.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.navigation.wxdh.vo.App;
import com.candou.ic.navigation.wxdh.vo.Job;
import com.candou.ic.navigation.wxdh.vo.Photo;
import com.candou.util.URLFetchUtil;

public class WXDH_AppParser {
    private static final int retryNumber = 5;
    private static ObjectMapper mapper;
    private static Logger log = Logger.getLogger(WXDH_AppParser.class);
    private static String baseUrl = "http://wx.ijinshan.com/data/info-%d.json";
    //private static String baseUrl = "http://wx.ijinshan.com/data/info-925.json";
    public static App parse(Job job) {
        int retryCounter = 0;
        String htmlSource = null;
        List<Photo> photos = new ArrayList<Photo>();

        String url = String.format(baseUrl, job.getId());

        do {
            htmlSource = URLFetchUtil.fetchGet(url);
            retryCounter++;
            if (retryCounter > 1) {
                log.info("retry connection: " + url);
            }
        } while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

        if (htmlSource == null || htmlSource.length() == 0) {
            log.info("htmlSource is null!");
            return null;
        }

        JsonNode appNodeList2 = getNode_app(htmlSource);

        JsonNode appNode = appNodeList2;
        if (appNode == null) {
            return null;
        }
        App app = new App();
        // ID忽略
        app.setWx_id(appNode.get("id").asText().trim());
        app.setWx_displayname(appNode.get("n").asText().trim());
        String category_cn = appNode.get("cn").asText().trim();
        if (category_cn.equals("明星名人")) {
            app.setWx_category_name("名人明星");
        }else if (category_cn.equals("其他账号")) {
            app.setWx_category_name("其他帐号");
        }else{
            app.setWx_category_name(category_cn);
        }

        app.setWx_date(appNode.get("dts").asText().trim());
        app.setWx_detail(appNode.get("di").asText().trim());
        app.setWx_intro(appNode.get("in").asText().trim());
        app.setWx_url(appNode.get("u").asText().trim());
        app.setWx_name(appNode.get("oc").asText().trim());
        app.setWx_views(Integer.parseInt(appNode.get("d").asText().trim()));

        // 辅助字段 远端URL地址
        app.setWx_icon_url(appNode.get("i").asText().trim());
        app.setWx_qrcode_url(appNode.get("imc").asText().trim());

        // 获取**高/低分辨率从it**图片路径photo
        photos.addAll(extracePhotos_high(appNodeList2, job.getId()));

        app.setPhotos(photos);

        return app;
    }

    public static JsonNode getNode_app(String json) {
        try {
            if (mapper == null) {
                mapper = new ObjectMapper();
            }

            JsonNode root = mapper.readTree(json);

            JsonNode results = root.get("data");
            if (results.size() == 0) {
                return null;
            }
            return results;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Photo> extracePhotos_high(JsonNode appNode, int appId) {
        List<Photo> photos = new ArrayList<Photo>();
        // 注意抓取ipad应用的时候将这里改成ipadScreenshotUrls
        // im 高分辨率 it低分辨率
        JsonNode systemRequirementsNode_im = appNode.get("im");
        if (systemRequirementsNode_im == null) {
            return photos;
        }
        int size_im = systemRequirementsNode_im.size();
        if (size_im == 0) {
            return photos;
        }

        JsonNode systemRequirementsNode_it = appNode.get("it");
        if (systemRequirementsNode_it == null) {
            return photos;
        }
        int size_it = systemRequirementsNode_it.size();
        if (size_it == 0) {
            return photos;
        }
        for (int index = 0; index < size_im + 1 - 1; index++) {
            String photoUrl_im = systemRequirementsNode_im.get(index).getTextValue();
            String photoUrl_it = systemRequirementsNode_it.get(index).getTextValue();
            Photo photo = new Photo();
            photo.setWx_id(appId);
            photo.setWx_screen_url(photoUrl_im);
            photo.setWx_thumb_url(photoUrl_it);

            photos.add(photo);
        }
        return photos;
    }

}
