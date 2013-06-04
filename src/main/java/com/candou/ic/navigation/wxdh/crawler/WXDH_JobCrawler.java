package com.candou.ic.navigation.wxdh.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.navigation.wxdh.dao.AppDao;
import com.candou.ic.navigation.wxdh.dao.CategoryDao;
import com.candou.ic.navigation.wxdh.dao.JobDao;
import com.candou.ic.navigation.wxdh.vo.Category;
import com.candou.ic.navigation.wxdh.vo.Job;
import com.candou.util.SeedUtil;
import com.candou.util.TextUtil;
import com.candou.util.URLFetchUtil;

public class WXDH_JobCrawler {



    /**
     * 微信导航 Job列表页面下载
     * @param args
     */


    private final String seedFile;
    private final int retryNumber = 5;
    private static ObjectMapper mapper;
    private static Logger log = Logger.getLogger(WXDH_JobCrawler.class);


    public WXDH_JobCrawler(String file) {
        seedFile = file;
    }

    public void start() {
        List<Category> categories = getCategory();
        List<Job> newJobs = new ArrayList<Job>();
        List<Job> matchedJobs = new ArrayList<Job>();
        String nopage ="404 Page Not Found";
        CategoryDao.addBatchCategory(categories);

        for (Category category : categories) {
            log.info(String.format("fetch category %s[%s]", category.getCname(), category.getCurl()));

            int page = 1;

            int retryCounter = 0;
            while (true) {
                String pageUrl = category.getCurl().concat("-p"+page +".json");

                String htmlSource = null;
                do {
                    htmlSource = URLFetchUtil.fetchGet(pageUrl);
                    retryCounter++;
                    if (retryCounter > 1) {
                        log.info("retry connection: " + pageUrl);
                    }
                } while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

                // if html source length equal zero
                if (htmlSource == null || htmlSource.length() == 0) {
                    continue;
                }

                if (htmlSource.indexOf(nopage) != -1) {
                    log.info("404 Page Not Found!");
                    break;
                }

                // analyze
                JsonNode appNodeList = getNode(htmlSource);

                if (appNodeList == null) {
                    break;
                }
                List<Job> jobs = new ArrayList<Job>();
                int size = appNodeList.size();
                for (int index = 0; index < size; index++) {
                    JsonNode appNode = appNodeList.get(index);

                    Job job = new Job();
                    job.setId(Integer.parseInt(appNode.get("id").asText()));
                    job.setName(appNode.get("n").asText());
                    job.setIntro(appNode.get("in").asText());
                    job.setUrl(appNode.get("u").asText());
                    job.setF(Integer.parseInt(appNode.get("f").asText()));
                    job.setOc((appNode.get("oc").asText()));
                    job.setCid(Integer.parseInt(appNode.get("cid").asText()));
                    job.setCname((appNode.get("cn").asText()));
                    job.setDirect_number(appNode.get("d").asText());

                    jobs.add(job);
                }


                for (Job job : jobs) {
                    if (!JobDao.exists(job)) {
                        newJobs.add(job);
                    }

                    if (AppDao.exists(job.getId())) {
                        matchedJobs.add(job);
                    }
                }
                //新job,入库
                if (newJobs.size() > 0) {
                    log.info("add batch jobs");
                    JobDao.addJob(newJobs);
                    newJobs.clear();
                }
                //已存在,更新
                if (matchedJobs.size() > 0) {
                    log.info("update matched jobs");
                    //JobDao.batchUpdate(matchedJobs);
                    matchedJobs.clear();
                }

                page++;//下一页
                retryCounter = 0;
            }
        }
    }

    public static JsonNode getNode(String json) {
        try {
            if (mapper == null) {
                mapper = new ObjectMapper();
            }
            JsonNode root = mapper.readTree(json);
            JsonNode results = root.get("data");
            if (results.isArray() && results.size() > 0) {
                return results;
            }else{
                log.info("404!");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<Category> getCategory() {
        List<Category> categories = new ArrayList<Category>();
        Map<String, String> seeds = TextUtil.getSeedList(SeedUtil.getSeedFile(seedFile));

        for (String name : seeds.keySet()) {
            String url = seeds.get(name);
            Category category = new Category();
            category.setCid(Integer.parseInt(url.substring(url.indexOf("category-") + 9)));
            category.setCname(name);
            category.setCurl(url);

            categories.add(category);
        }

        return categories;
    }




}
