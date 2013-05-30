package com.candou.ic.navigation.jcwx.crawler.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.navigation.jcwx.bean.App;
import com.candou.ic.navigation.jcwx.bean.Article;
import com.candou.ic.navigation.jcwx.bean.Job;
import com.candou.ic.navigation.jcwx.bean.Like;
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

        List<Article> articles = new ArrayList<Article>();
        for (int index = 0; index < articleNode.size(); index++) {
            Article article = new Article();
            article.setFirstCid(job.getCid());
            article.setFirstCname(job.getCname());
            article.setArticleId(Integer.parseInt(articleNode.get(index).get("id").asText()));
            article.setTitle(articleNode.get(index).get("title").asText());
            article.setSecondCname(articleNode.get(index).get("category").asText());
            article.setViews(Integer.parseInt(articleNode.get(index).get("views").asText()));
            article.setLike(Integer.parseInt(articleNode.get(index).get("like").asText()));
            article.setThumbnail(articleNode.get(index).get("thumbnail").asText());
            article.setWxh(articleNode.get(index).get("wxh").asText());
            article.setWxqr(articleNode.get(index).get("wxqr").asText());
            article.setContent(articleNode.get(index).get("content").asText());
            article.setUpdatedAt(DateTimeUtil.nowDateTime());
            article.setCreatedAt(DateTimeUtil.nowDateTime());
            articles.add(article);
        }
        app.setArticles(articles);

        if (articles.isEmpty()) {
            return null;
        }

        // like
        JsonNode likeNode = getNode(htmlSource, "WeiXin_Like");
        if (likeNode == null) {
            return app;
        }

        List<Like> likes = new ArrayList<Like>();
        for (int index = 0; index < likeNode.size(); index++) {
            Like like = new Like();
            like.setArticleId(articles.get(0).getArticleId());
            like.setLikeId(Integer.parseInt(likeNode.get(index).get("id").asText()));
            like.setTitle(likeNode.get(index).get("title").asText());
            like.setThumbnail(likeNode.get(index).get("thumbnail").asText());
            like.setCreatedAt(DateTimeUtil.nowDateTime());
            like.setUpdatedAt(DateTimeUtil.nowDateTime());
            likes.add(like);
        }
        app.setLikes(likes);

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
