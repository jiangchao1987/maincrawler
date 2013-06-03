package com.candou.ic.navigation.jcwx.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.candou.ic.navigation.jcwx.bean.Category;
import com.candou.ic.navigation.jcwx.bean.Job;
import com.candou.ic.navigation.jcwx.dao.CategoryDao;
import com.candou.ic.navigation.jcwx.dao.JobDao;
import com.candou.util.DateTimeUtil;
import com.candou.util.SeedUtil;
import com.candou.util.TextUtil;
import com.candou.util.URLFetchUtil;

/**
 * 精彩微信 Job爬虫逻辑。
 *
 * @author jiangchao
 */
public class JCWX_JobCrawler {

    private final String seedFile;
    private final int retryNumber = 5;
    private static ObjectMapper mapper;
    private static Logger log = Logger.getLogger(JCWX_JobCrawler.class);

    public JCWX_JobCrawler(String file) {
        seedFile = file;
    }

    public void start() {
        List<Category> categories = getCategory();
        List<Job> newJobs = new ArrayList<Job>();

        CategoryDao.addBatchCategory(categories);

        for (Category category : categories) {
            log.info(String.format("fetch category %s[%s]", category.getCname(), category.getCurl()));

            int page = 1;

            int retryCounter = 0;
            while (true) {
                String pageUrl = category.getCurl().concat("&page=" + page);

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

                // analyze
                JsonNode appNodeList = getNode(htmlSource);

                if (appNodeList == null) {
                    break;
                }

                int size = appNodeList.size();
                for (int index = 0; index < size; index++) {
                    JsonNode appNode = appNodeList.get(index);

                    if(JobDao.isExist(Integer.parseInt(appNode.get("id").asText()))){
                        continue;
                    }

                    Job job = new Job();
                    job.setId(Integer.parseInt(appNode.get("id").asText()));
                    job.setTitle(appNode.get("thumbnail").asText());
                    job.setWxh(appNode.get("wxh").asText());
                    job.setCname(appNode.get("category").asText());
                    job.setViews(Integer.parseInt(appNode.get("views").asText()));
                    job.setContent(appNode.get("content").asText());
                    job.setThumbnail(appNode.get("thumbnail").asText());
                    job.setCreatedAt(DateTimeUtil.nowDateTime());
                    job.setUpdatedAt(DateTimeUtil.nowDateTime());
                    job.setCid(category.getCid());
                    job.setCname(category.getCname());

                    newJobs.add(job);
                }

                if (newJobs.size() > 0) {
                    log.info("add batch jobs");
                    JobDao.addBatchJobs(newJobs);
                    newJobs.clear();
                }

                page++;
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
            JsonNode results = root.get("ItemList");
            if (results.isArray() && results.size() > 0) {
                return results;
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
            category.setCid(Integer.parseInt(url.substring(url.indexOf("id=") + 3)));
            category.setCname(name);
            category.setCurl(url);

            categories.add(category);
        }

        return categories;
    }

}
