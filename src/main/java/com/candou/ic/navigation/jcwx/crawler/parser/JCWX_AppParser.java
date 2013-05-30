package com.candou.ic.navigation.jcwx.crawler.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.navigation.jcwx.bean.App;
import com.candou.ic.navigation.jcwx.bean.Job;
import com.candou.ic.navigation.jcwx.bean.Like;
import com.candou.ic.navigation.jcwx.dao.LikeDao;
import com.candou.util.DateTimeUtil;
import com.candou.util.URLFetchUtil;

public class JCWX_AppParser {

    private static final int retryNumber = 5;
    private static ObjectMapper mapper;
    private static Logger log = Logger.getLogger(JCWX_AppParser.class);
    private static String baseUrl = "http://www.weixin111.com/appapi/?appkey=08031EEevq1ZeVN0DtKGcebowgEPLtASJfBBn6iOTQ&ac=article&id=%d";

    public static App parse(Job job) {
        int retryCounter = 0;
        String htmlSource = null;

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

        // analyze
        // article
        JsonNode articleNode = getNode(htmlSource, "WeiXin_Article");

        if (articleNode == null) {
            return null;
        }

        App app = new App();
        app.setFirstCid(job.getCid());
        app.setFirstCname(job.getCname());
        app.setApp_id(Integer.parseInt(articleNode.get(0).get("id").asText()));
        app.setName(articleNode.get(0).get("title").asText());
        app.setSecondCname(articleNode.get(0).get("category").asText());
        app.setViews(Integer.parseInt(articleNode.get(0).get("views").asText()));
        app.setLike(Integer.parseInt(articleNode.get(0).get("like").asText()));
        app.setThumbnail(articleNode.get(0).get("thumbnail").asText());
        app.setWxh(articleNode.get(0).get("wxh").asText());
        app.setWxqr(articleNode.get(0).get("wxqr").asText());
        app.setContent(articleNode.get(0).get("content").asText());
        app.setUpdatedAt(DateTimeUtil.nowDateTime());
        app.setCreatedAt(DateTimeUtil.nowDateTime());
        System.out.println(articleNode.toString());
        // like
        JsonNode likeNode = getNode(htmlSource, "WeiXin_Like");
        if (likeNode != null) {
            List<Like> likes = new ArrayList<Like>();
            for(int i=0;i<likeNode.size();i++){
                Like like = new Like();
                like.setAppId(app.getApp_id());
                like.setLikeId(Integer.parseInt(likeNode.get(i).get("id").asText()));
                like.setName(likeNode.get(i).get("title").asText());
                like.setThumbnail(likeNode.get(i).get("thumbnail").asText());
                likes.add(like);
            }
            LikeDao.addBatchLikes(likes);
        }
        System.out.println(likeNode);

        return app;
    }

    public static JsonNode getNode(String json, String flag) {
        try {
            if (mapper == null) {
                mapper = new ObjectMapper();
            }
            JsonNode root = mapper.readTree(json);
            JsonNode results = root.get(flag);
            if (results.isArray() && results.size() > 0) {
                return results;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
