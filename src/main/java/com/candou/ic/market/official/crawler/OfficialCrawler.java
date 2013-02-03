package com.candou.ic.market.official.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.candou.ic.market.official.crawler.parser.LookupParser;
import com.candou.ic.market.pp.bean.Job;
import com.candou.ic.market.pp.dao.JobDao;
import com.candou.util.URLFetchUtil;

public class OfficialCrawler {
	private static Logger log = Logger.getLogger(OfficialCrawler.class);

	private String baseUrl = null;
	private final int retryNumber = 5;

	// private String[] rankAllUrls = { "https://itunes.apple.com/cn/rss/topfreeapplications/limit=300/json",
	// "http://itunes.apple.com/cn/rss/toppaidapplications/limit=300/json",
	// "http://itunes.apple.com/cn/rss/topgrossingapplications/limit=300/json" };

	private String[] rankAllUrls = { "http://itunes.apple.com/cn/rss/topfreeipadapplications/limit=300/json",
			"http://itunes.apple.com/cn/rss/toppaidipadapplications/limit=300/json",
			"http://itunes.apple.com/cn/rss/topgrossingipadapplications/limit=300/json" };

	// private String[] rankCatlogUrls = { "http://itunes.apple.com/cn/rss/topfreeapplications/limit=300/genre=%d",
	// "http://itunes.apple.com/cn/rss/toppaidapplications/limit=300/genre=%d",
	// "http://itunes.apple.com/cn/rss/topgrossingapplications/limit=300/genre=%d" };

	private String[] rankCatlogUrls = { "http://itunes.apple.com/cn/rss/topfreeipadapplications/limit=300/genre=%d",
			"http://itunes.apple.com/cn/rss/toppaidipadapplications/limit=300/genre=%d",
			"http://itunes.apple.com/cn/rss/topgrossingipadapplications/limit=300/genre=%d" };

	// 无法区分iphone/ipad
	private String[] rankLatestUrls = { "https://itunes.apple.com/cn/rss/newapplications/limit=300/genre=6014/json",
			"https://itunes.apple.com/cn/rss/newfreeapplications/limit=300/genre=6014/json",
			"https://itunes.apple.com/cn/rss/newpaidapplications/limit=300/genre=6014/json" };

	private int[] rankCatlogIds = { 6000, 6001, 6002, 6003, 6004, 6005, 6006, 6007, 6008, 6009, 6010, 6011, 6012, 6013,
			6014, 6015, 6016, 6017, 6018, 6020, 6022, 6023 };

	public void start() {
		while (true) {
			all();
			catlog();
			latest();
			// sleep 1 hour
			try {
				log.info("sleep 1 hour");
				Thread.sleep(60 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void latest() {
		String htmlSource = null;
		int retryCounter = 0;
		for (int x = 0; x < rankLatestUrls.length; x++) {
			do {
				baseUrl = rankLatestUrls[x];
				htmlSource = URLFetchUtil.fetchGet(baseUrl);
				retryCounter++;
				if (retryCounter > 1) {
					log.info("retry connection: " + baseUrl);
				}
			} while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

			retryCounter = 0;

			// if html source length equal zero
			if (htmlSource == null || htmlSource.length() == 0) {
				continue;
			}

			List<Job> list = parse(htmlSource.trim());

			if (list == null || list.isEmpty()) {
				continue;
			}
			exec(list);
			// sleep 10 seconds
			try {
				log.info("sleep 5s");
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void catlog() {
		String htmlSource = null;
		int retryCounter = 0;
		for (int x = 0; x < rankCatlogUrls.length; x++) {
			for (int pn = 0; pn < rankCatlogIds.length; pn++) {
				do {
					baseUrl = String.format(rankCatlogUrls[x], pn);
					htmlSource = URLFetchUtil.fetchPost(baseUrl);
					retryCounter++;
					if (retryCounter > 1) {
						log.info("retry connection: " + baseUrl);
					}
				} while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

				retryCounter = 0;

				// if html source length equal zero
				if (htmlSource == null || htmlSource.length() == 0) {
					continue;
				}

				htmlSource = htmlSource.replaceAll("﻿\\{", "\\{");
				List<Job> list = parse(htmlSource.trim());

				if (list == null || list.isEmpty()) {
					continue;
				}
				exec(list);
				// sleep 10 seconds
				try {
					log.info("sleep 5s");
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void all() {
		String htmlSource = null;
		int retryCounter = 0;
		for (int x = 0; x < rankAllUrls.length; x++) {
			do {
				baseUrl = rankAllUrls[x];
				htmlSource = URLFetchUtil.fetchGet(baseUrl);
				retryCounter++;
				if (retryCounter > 1) {
					log.info("retry connection: " + baseUrl);
				}
			} while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

			retryCounter = 0;

			// if html source length equal zero
			if (htmlSource == null || htmlSource.length() == 0) {
				continue;
			}

			htmlSource = htmlSource.replaceAll("﻿\\{", "\\{");
			List<Job> list = parse(htmlSource.trim());

			if (list == null || list.isEmpty()) {
				continue;
			}
			exec(list);
			// sleep 10 seconds
			try {
				log.info("sleep 5s");
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private List<Job> parse(String source) {
		// 正则过滤出id
		List<Integer> ids = new ArrayList<Integer>();
		String regex = "\"im:id\":\"(.*?)\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		while (m.find()) {
			String temp = m.group(1);
			if (temp != null && temp.length() == 9) {
				ids.add(Integer.parseInt(temp));
			}
		}

		log.info(String.format("ids.size: %d", ids.size()));

		// 走lookup接口拿到必须信息
		List<Job> jobs = new ArrayList<Job>();
		for (int index = 0; index < ids.size(); index++) {
			Job job = parseLookup(ids.get(index));
			if (job != null) {
				jobs.add(job);
			}
		}
		return jobs;
	}

	private Job parseLookup(int id) {
		return LookupParser.parse(id);
	}

	/**
	 * 1、id存在，更新价格；比较version和releaseDate，如果不一致插入更新记录 2、id不存在，插入ApplicationJob
	 */
	private void exec(List<Job> list) {
		List<Job> newJobs = new ArrayList<Job>();
		List<Job> matchedJobs = new ArrayList<Job>();
		for (int index = 0; index < list.size(); index++) {
			Job currentJob = list.get(index);
			if (JobDao.exists(currentJob)) {
				matchedJobs.add(currentJob);
			} else {
				newJobs.add(currentJob);
			}
		}
		log.info(String.format("newJobs.size() : %d, matchedJobs.size(): %d", newJobs.size(), matchedJobs.size()));
		JobDao.addBatchJobs(newJobs);
		JobDao.batchUpdate(matchedJobs);
		newJobs.clear();
		matchedJobs.clear();
	}

}
