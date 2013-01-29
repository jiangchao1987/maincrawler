package com.candou.ic.top.appstatics.crawler;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.top.appstatics.bean.App;
import com.candou.util.URLFetchUtil;

public class AppstaticsCrawler {
    private final int retryNumber = 5;
    private static Logger log = Logger.getLogger(AppstaticsCrawler.class);
	private String baseUrl = "http://appstatics.com/categories/0?platform=101&country=143465&list=501";
	private static ObjectMapper mapper;
	
	public void start() {
        String htmlSource = null;
        int retryCounter = 0;
        do {
            htmlSource = URLFetchUtil.fetchGet(baseUrl);
            retryCounter++;
            if (retryCounter > 1) {
                log.info("retry connection: " + baseUrl);
            }
        }
        while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

        // if html source length equal zero
        if (htmlSource == null || htmlSource.length() == 0) {
            return;
        }

        List<App> list = parse(htmlSource);
//        RankDao.addBatchApps(list);
    }
	
	private List<App> parse(String htmlSource) {
		JsonNode root = getNode(htmlSource);
		System.out.println(root.size());
		if (root == null || root.size() == 0) {
			return null;
		}
		for (int index = 0; index < root.size(); index++) {
			System.out.println(root.get(index));
		}
		return null;
	}
	
	public JsonNode getNode(String json) {
		try {
			if (mapper == null) {
				mapper = new ObjectMapper();
			}
			JsonNode root = mapper.readTree(json);
			if (root.isArray() && root.size() > 0) {
				return root;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
