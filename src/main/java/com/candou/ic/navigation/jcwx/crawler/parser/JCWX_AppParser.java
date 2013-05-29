package com.candou.ic.navigation.jcwx.crawler.parser;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.navigation.jcwx.bean.App;
import com.candou.ic.navigation.jcwx.bean.Job;
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
        System.out.println(articleNode.toString());
        // like
        JsonNode likeNode = getNode(htmlSource, "WeiXin_Like");
        if (likeNode == null) {
            return null;
        }
        System.out.println(likeNode);

        return null;
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
