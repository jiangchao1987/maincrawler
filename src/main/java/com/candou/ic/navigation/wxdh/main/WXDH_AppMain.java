package com.candou.ic.navigation.wxdh.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import com.candou.ac.rom.bean.RomPhoto;
import com.candou.ac.rom.downloader.RomImageDownloader;
import com.candou.ic.navigation.wxdh.dao.AppDao;
import com.candou.ic.navigation.wxdh.dao.CategoryDao;
import com.candou.ic.navigation.wxdh.dao.JobDao;
import com.candou.ic.navigation.wxdh.dao.PhotoDao;
import com.candou.ic.navigation.wxdh.imgload.PhotoDownloader;
import com.candou.ic.navigation.wxdh.vo.App;
import com.candou.ic.navigation.wxdh.vo.Category;
import com.candou.ic.navigation.wxdh.vo.Job;
import com.candou.ic.navigation.wxdh.vo.Photo;
import com.candou.util.BrowserUtil;
import com.candou.util.URLFetchUtil;

/**
 * @author aifeng.liu
 *
 */
public class WXDH_AppMain {

    private static HtmlCleaner cleaner;
    private final int retryNumber = 5;
    private static Logger log = Logger.getLogger(WXDH_AppMain.class);
    private static ObjectMapper mapper;
    private int counter = 0;
    private int count = 0;
    private static List<Job> jobs = new ArrayList<Job>();
    private static List<App> apps = new ArrayList<App>();
    private static List<Photo> photos = new ArrayList<Photo>();

    public void start() {
        while (true) {
            count++;
            log.info("正在处理第:" + count + "页---------------------------------");

            List<Category> cates = CategoryDao.findAllCategortys();

            log.info(cates.size());
            // 以分类开始遍历
            for (Category category : cates) {
                int cateid = category.getCid();
                String pageUrl = "http://wx.ijinshan.com/data/category-" + cateid + "-p" + count + ".json";
                log.info(String.format("fetch [%s]", pageUrl));

                String htmlSource = getHtmlContent(pageUrl);
                if (htmlSource == null) {
                    if (counter > retryNumber) {
                        break;
                    }
                    counter++;
                    continue;
                }
                counter = 0;

                // 解析json,set实体,入库
                // analyze 分析
                JsonNode appNodeList = getNode(htmlSource);
                if (appNodeList == null) {
                    break;
                }
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
                    job.setIcon((appNode.get("i").asText()));
                    job.setDirect_number(appNode.get("d").asText());
                    jobs.add(job);
                }
                // 入库
                if (jobs.isEmpty()) {
                    break;
                }
                log.info("分类ID:------" + cateid + "        分类名称:------" + category.getCname());
                log.info("jobs集合元素总数:---" + jobs.size());
                JobDao.addJob(jobs);

                // 下载job icon
                int downloadCount = 1;
                for (Job job : jobs) {
                    try {
                        PhotoDownloader.downloadIcon_job(job);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    log.info("已下载 " + downloadCount + " icon,还有 " + (jobs.size() - downloadCount) + "没下载");
                    downloadCount++;
                }

                jobs.clear();//清空
                // -------------------------
                // 至此,列表页面,job全部入库
            }
            // go on,二级页面 app
            // url: http://wx.ijinshan.com/data/info-67.json
            // app 的id值,就是app的id . 如上的67

            jobs = JobDao.findAllJobs();
            log.info("----------" + jobs.size());
            for (Job job : jobs) {
                int appid = job.getId();
                String pageUrl_app = "http://wx.ijinshan.com/data/info-" + appid + ".json";

                log.info(String.format("fetch [%s]", pageUrl_app));

                String htmlSource = getHtmlContent(pageUrl_app);
                if (htmlSource == null) {

                    if (counter > retryNumber) {
                        break;
                    }
                    counter++;
                    continue;
                }
                counter = 0;
                // 解析json,set实体,入库

                // analyze
                JsonNode appNodeList2 = getNode_app(htmlSource);
                if (appNodeList2 == null) {
                    break;
                }
                // 获取**低分辨率从it**图片路径photo
                photos = extracePhotos(appNodeList2, appid);
                // 入库
                PhotoDao.addBatchPhotos(photos);
                // 下载photo
                int downloadCount = 1;
                for (Photo photo : photos) {
                    try {
                        PhotoDownloader.downloadPhoto(photo);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    log.info("已下载 " + downloadCount + " photo,还有 " + (apps.size() - downloadCount) + "没下载");
                    downloadCount++;
                }
                // 获取**高分辨率从it**图片路径photo
                photos = extracePhotos_high(appNodeList2, appid);
                // 入库
                PhotoDao.addBatchPhotos(photos);
                // 下载photo
                for (Photo photo : photos) {
                    try {
                        PhotoDownloader.downloadPhoto_im(photo);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    log.info("已下载 " + downloadCount + " photo,还有 " + (apps.size() - downloadCount) + "没下载");
                    downloadCount++;
                }
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
                apps.add(app);
                // 入库
                if (apps.isEmpty()) {
                    break;
                }
                AppDao.addBatchApps(apps);
                // 下载app icon
                for (App app_icon : apps) {
                    try {
                        PhotoDownloader.downloadIcon_app(app_icon);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    log.info("已下载 " + downloadCount + " icon,还有 " + (apps.size() - downloadCount) + "没下载");
                    downloadCount++;
                }
                // 下载app imc(二维码)
                for (App app_imc : apps) {
                    try {
                        PhotoDownloader.downloadIcon_app_imc(app_imc);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    log.info("已下载 " + downloadCount + " imc,还有 " + (apps.size() - downloadCount) + "没下载");
                    downloadCount++;
                }

            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getRedirectDownloadURL("http://www.shendu.com/?c=download&a=index&source=rom&id=387"));
    }

    /**
     * @param downloadURL
     *            下载地址
     * @return 转向后的地址
     */
    public static String getRedirectDownloadURL(String downloadURL) {
        String redirectDownloadURL = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        params.setParameter(ClientPNames.HANDLE_REDIRECTS, false); // 关闭GET 重定向
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
        try {
            HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
            HttpGet httpget = new HttpGet(downloadURL);
            HttpResponse response = httpclient.execute(httpget);
            int statuscode = response.getStatusLine().getStatusCode();
            // Http statecode 为3XX，进行转向
            if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
                || (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                Header header = response.getFirstHeader("Location");
                if (header != null) {
                    redirectDownloadURL = header.getValue().trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("failed to get redirectdownloadurl.");
        }
        return redirectDownloadURL;
    }

    public static List<RomPhoto> photoDownloader(List<RomPhoto> remotePhotos) {
        List<RomPhoto> localPhotos = new ArrayList<RomPhoto>();
        for (RomPhoto photo : remotePhotos) {
            String localImageUrl = RomImageDownloader.downloader(photo.getOriginalUrl());
            RomPhoto newPhoto = new RomPhoto();
            newPhoto.setAppId(photo.getAppId());
            newPhoto.setOriginalUrl(localImageUrl);
            localPhotos.add(newPhoto);
        }
        return localPhotos;
    }

    /**
     * @param url
     *            httpUrl
     * @return 页面内容(html code)
     */
    public String getHtmlContent(String url) {
        int retryCounter = 0;
        String htmlSource = null;
        do {
            htmlSource = URLFetchUtil.fetchGet(url);
            retryCounter++;
            if (retryCounter > 1) {
                log.info("retry connection: " + url);
            }
        } while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));
        if (htmlSource == null || htmlSource.length() == 0 || htmlSource.contains("404 NOT FOUND")) {
            log.info("404!!!");
            return null;
        }
        return htmlSource;
    }

    public static HtmlCleaner getCleaner() {
        if (cleaner == null) {
            cleaner = new HtmlCleaner();
            CleanerProperties props = cleaner.getProperties();
            props.setAllowHtmlInsideAttributes(true);
            props.setAllowMultiWordAttributes(true);
            props.setRecognizeUnicodeChars(true);
            props.setRecognizeUnicodeChars(true);
            props.setTranslateSpecialEntities(true);
            props.setAdvancedXmlEscape(true);
            props.setOmitComments(true);
        }
        return cleaner;
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
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
