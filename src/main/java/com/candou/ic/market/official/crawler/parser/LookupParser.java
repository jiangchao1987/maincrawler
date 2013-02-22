package com.candou.ic.market.official.crawler.parser;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.market.pp.bean.Job;

public class LookupParser {
	private static final int retryNumber = 5;
	private static Logger log = Logger.getLogger(LookupParser.class);
	private static ObjectMapper mapper;
	private static String lookApi = "http://itunes.apple.com/lookup?id=%d&country=cn";
	public static int[] categoryIds = { 6000, 6001, 6002, 6003, 6004, 6005, 6006, 6007, 6008, 6009, 6010, 6011, 6012,
			6013, 6014, 6015, 6016, 6017, 6018, 6020, 6022, 6023};
	public static String[] categoryNames = { "Business", "Weather", "Tool", "Travel", "Sports", "Social", "Refer",
			"Ability", "Photography", "News", "Gps", "Music", "Life", "Health", "Game", "Finance", "Pastime",
			"Education", "Book", "Medical", "Catalogs", "FoodDrink"};

	public static Job parse(Integer id) {
		int retryCounter = 0;
		String htmlSource = null;

		String url = String.format(lookApi, id);

		do {
			htmlSource = com.candou.util.URLFetchUtil.fetchGet(url);
			retryCounter++;
			if (retryCounter > 1) {
				log.info("retry connection: " + url);
			}
		} while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

		if (htmlSource == null || htmlSource.length() == 0) {
			return null;
		}

		// analyze
		JsonNode appNode = getNode(htmlSource);

		if (appNode == null) {
			return null;
		}

		Job job = new Job();
		
		//抓取iphone使用
		if (!supportIphone(appNode)) {
			return null;
		}
		//抓取ipad使用
//		if (!supportIpad(appNode)) {
//			return null;
//		}

		job.setId(id);
		job.setName(extraceName(appNode));
		job.setUrl(String.format("https://itunes.apple.com/cn/app/id%d?mt=8", id));
		job.setPrice(extracePrice(appNode));
		job.setPriceCurrency("RMB");

		int catid = extraceCategoryId(appNode);
		int index = 14;
		for (int j = 0; j < categoryIds.length; j++) {
			if (catid == categoryIds[j]) {
				index = j;
			}
		}
		String cname = categoryNames[index];

		job.setCategoryID(catid);
		job.setCategoryName(cname);
		job.setVersion(extraceVersion(appNode));
		job.setReleaseDate(extraceReleaseDate(appNode));
		
		log.info(job);
		
		return job;
	}
	
	public static boolean supportIpad(JsonNode appNode) {
		JsonNode valueNode = appNode.get("ipadScreenshotUrls");
		if (valueNode == null || valueNode.size() == 0) {
			return false;
		}
		return true;
	}
	
	public static boolean supportIphone(JsonNode appNode) {
		JsonNode valueNode = appNode.get("screenshotUrls");
		if (valueNode == null || valueNode.size() == 0) {
			return false;
		}
		return true;
	}

	public static float extraceStarOfOverall(JsonNode appNode) {
		JsonNode valueNode = appNode.get("averageUserRating");
		return valueNode == null ? 0 : (float) valueNode.asDouble();
	}

	public static float extraceStarOfCurrentVersion(JsonNode appNode) {
		JsonNode valueNode = appNode.get("averageUserRatingForCurrentVersion");
		return valueNode == null ? 0 : (float) valueNode.asDouble();
	}

	public static String extraceReleaseDate(JsonNode appNode) {
		String releaseDate = getValue(appNode.get("releaseDate"));
		if (releaseDate.isEmpty() || releaseDate.length() < 10) {
			return "";
		}
		return releaseDate.substring(0, 10);
	}

	private static int extraceCategoryId(JsonNode appNode) {
		JsonNode valueNode = appNode.get("primaryGenreId");
		return valueNode == null ? 0 : valueNode.asInt();
	}

	public static float extracePrice(JsonNode appNode) {
		JsonNode valueNode = appNode.get("price");
		return valueNode == null ? 0 : (float) valueNode.asDouble();
	}

	public static String extraceName(JsonNode appNode) {
		return getValue(appNode.get("trackName"));
	}

	public static String extraceVersion(JsonNode appNode) {
		return getValue(appNode.get("version"));
	}

	private static String getValue(JsonNode valueNode) {
		if (valueNode == null) {
			return "";
		}
		return valueNode.getTextValue();
	}

	public static JsonNode getNode(String json) {
		try {
			if (mapper == null) {
				mapper = new ObjectMapper();
			}
			JsonNode root = mapper.readTree(json);
			JsonNode results = root.get("results");
			if (results.isArray() && results.size() > 0) {
				return results.get(0);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
