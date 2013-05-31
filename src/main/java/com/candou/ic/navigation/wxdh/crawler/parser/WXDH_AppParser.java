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
            return null;
        }

        JsonNode appNodeList2 = getNode_app(htmlSource);

        JsonNode appNode = appNodeList2;
        App app = new App();
        app.setId(Integer.parseInt(appNode.get("id").asText()));
        app.setName(appNode.get("n").asText());
        app.setIntro(appNode.get("in").asText());
        app.setUrl(appNode.get("u").asText());
        app.setF(Integer.parseInt(appNode.get("f").asText()));
        app.setOc(appNode.get("oc").asText());
        app.setWsu(appNode.get("wsu").asText());
        app.setDetail(appNode.get("di").asText());
        app.setDts(Integer.parseInt(appNode.get("dts").asText()));
        app.setCid(Integer.parseInt(appNode.get("cid").asText()));
        app.setCname(appNode.get("cn").asText());
        app.setIcon(appNode.get("i").asText());
        app.setImc(appNode.get("imc").asText());
        app.setSc(appNode.get("sc").asText());
        app.setDirect_number(appNode.get("d").asText());

        // 获取**低分辨率从it**图片路径photo
        photos.addAll(extracePhotos(appNodeList2, job.getId()));
        // 获取**高分辨率从it**图片路径photo
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
            return results;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Photo> extracePhotos(JsonNode appNode, int appId) {
        List<Photo> photos = new ArrayList<Photo>();
        // 注意抓取ipad应用的时候将这里改成ipadScreenshotUrls
        // it 低分辨率
        JsonNode systemRequirementsNode = appNode.get("it");
        if (systemRequirementsNode == null) {
            return photos;
        }
        int size = systemRequirementsNode.size();
        if (size == 0) {
            return photos;
        }
        for (int index = 0; index < size + 1 - 1; index++) {
            String photoUrl = systemRequirementsNode.get(index).getTextValue();
            Photo photo = new Photo();
            photo.setAppid(appId);
            photo.setPhoto_url(photoUrl);
            photo.setType(0);
            photos.add(photo);
        }
        return photos;
    }

    public static List<Photo> extracePhotos_high(JsonNode appNode, int appId) {
        List<Photo> photos = new ArrayList<Photo>();
        // 注意抓取ipad应用的时候将这里改成ipadScreenshotUrls
        // im 高分辨率
        JsonNode systemRequirementsNode = appNode.get("im");
        if (systemRequirementsNode == null) {
            return photos;
        }
        int size = systemRequirementsNode.size();
        if (size == 0) {
            return photos;
        }
        for (int index = 0; index < size + 1 - 1; index++) {
            String photoUrl = systemRequirementsNode.get(index).getTextValue();
            Photo photo = new Photo();
            photo.setAppid(appId);
            photo.setPhoto_url(photoUrl);
            photo.setType(1);
            photos.add(photo);
        }
        return photos;
    }

}
