package com.candou.ic.market.pp.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.candou.ic.market.pp.bean.Job;
import com.candou.ic.market.pp.dao.JobDao;
import com.candou.util.URLFetchUtil;

public class FreeCrawler {
	private final int retryNumber = 5;
	private static Logger log = Logger.getLogger(FreeCrawler.class);
	private String baseUrl = null;
	public static int[] categoryIds = { 6000, 6001, 6002, 6003, 6004, 6005, 6006, 6007, 6008, 6009, 6010, 6011, 6012,
			6013, 6014, 6015, 6016, 6017, 6018, 6020, 6022, 6023, 7001, 7002, 7003, 7004, 7005, 7006, 7007, 7008, 7009,
			7010, 7011, 7012, 7013, 7014, 7015, 7016, 7017, 7018, 7019 };
	public static String[] categoryNames = { "Business", "Weather", "Tool", "Travel", "Sports", "Social", "Refer",
			"Ability", "Photography", "News", "Gps", "Music", "Life", "Health", "Game", "Finance", "Pastime",
			"Education", "Book", "Medical", "Catalogs", "FoodDrink", "ActionGame", "ExploreGame", "ArcadeGame",
			"DesktopGame", "PokerGame", "CasinoGame", "DiceGame", "EducationGame", "FamilyGame", "ChildGame",
			"MusicGame", "IntelligenceGame", "RacingGame", "RoleGame", "SimulationGame", "SportsGame", "StrategyGame",
			"SmallGame", "WordGame" };

	public void start(String[] sources) {
		while (true) {
			String htmlSource = null;
			int retryCounter = 0;
			for (int x = 0; x < sources.length; x++) {
				for (int pn = 1; pn < 1000; pn++) {
					do {
						baseUrl = String.format(sources[x], pn);
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
			// sleep 1 hour
			try {
				log.info("sleep 1 hour");
				Thread.sleep(60 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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

	private List<Job> parse(String htmlSource) {
		List<Job> jobs = new ArrayList<Job>();
		try {
			JSONObject jsonObject = new JSONObject(htmlSource);
			JSONArray jsonArray = jsonObject.getJSONArray("contentArea");
			if (jsonArray == null || jsonArray.length() == 0) {
				return null;
			}
			for (int i = 0; i < jsonArray.length(); i++) {
				Job job = new Job();
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				int itemid = jsonObject2.getInt("itemid");
				String title = jsonObject2.getString("title");
				String version = jsonObject2.getString("version");
				int catid = jsonObject2.getInt("catid");
				String releaseDate = jsonObject2.getString("updatetime");
				float price = (float) jsonObject2.getDouble("timeprice");

				int index = 14;
				for (int j = 0; j < categoryIds.length; j++) {
					if (catid == categoryIds[j]) {
						index = j;
					}
				}
				String cname = categoryNames[index];

				job.setId(itemid);
				job.setName(title);
				job.setUrl(String.format("https://itunes.apple.com/cn/app/id%d?mt=8", itemid));
				job.setPrice(price);
				job.setPriceCurrency("RMB");
				job.setCategoryID(catid);
				job.setCategoryName(cname);
				job.setVersion(version);
				job.setReleaseDate(releaseDate);

				log.info(job);
				jobs.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jobs;
	}

}
