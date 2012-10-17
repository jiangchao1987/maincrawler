package com.candou.ac.rom.crawler;

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
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.bean.RomJob;
import com.candou.ac.rom.bean.RomPhoto;
import com.candou.ac.rom.dao.DaoFactory;
import com.candou.ac.rom.downloader.RomImageDownloader;
import com.candou.util.BrowserUtil;
import com.candou.util.URLFetchUtil;

public class RomCrawler {
    private static HtmlCleaner cleaner;
    private final int retryNumber = 5;
    private static Logger log = Logger.getLogger(RomCrawler.class);
    private static String xpath = "//div[@class='rom_introduct']/h2/a";
    private static String xpathAuthor = "//div[@class='rom_de_jie']/ul/li[2]/span";
    private static String xpathFitType = "//div[@class='rom_de_jie']/ul/li[1]";
    private static String xpathSize = "//div[@class='rom_de_jie']/ul/li[3]/span";
    private static String xpathReleaseDate = "//div[@class='rom_de_jie']/ul/li[4]/span";
    private static String xpathRomType = "//div[@class='rom_de_jie']/ul/li[5]";
    private static String xpathStar = "//span[@class='star_hui fl']/b[@class='star_7']";
    private static String xpathDescription = "//div[@class='jieshao_normal']";
    private static String xpathIconUrl = "//div[@class='rom_picture']/img";
    private static String xpathPhotos = "//div[@class='container_hover']/div[@class='pics'][*]/a/img";
    private static String xpathDownloadUrl = "//div[@class='rom_button']/a";
    private static String xpathCategory = "//div[@class='rom_head']/span[1]/a[4]";
    private static String xpathCompany = "//div[@class='rom_head']/span[1]/a[3]";
    private int counter = 0;

    public void start() {
        int page = 0;

        while (true) {
            List<RomPhoto> photos = new ArrayList<RomPhoto>();
            
            String pageUrl = "http://www.shendu.com/android/rom-cid-0-page-" + page++ + "-order-time.html";

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
            
            List<RomJob> jobs = getJobs(htmlSource);

            if (jobs.isEmpty()) {
                break;
            }
            List<RomApp> apps = new ArrayList<RomApp>();
            for (RomJob job : jobs) {
                RomApp app = getApp(job);
                if (app != null) {
                    apps.add(app);
                }
            }
            
            DaoFactory.getRomAppDao().addBatchApps(apps);
            for (RomApp app : apps) {
                photos.addAll(app.getPhotos());
            }
            DaoFactory.getRomPhotoDao().addBatchPhotos(photos);

        }
    }

    private RomApp getApp(RomJob job) {
        RomApp app = new RomApp();
        
        // whether exists
        if (DaoFactory.getRomAppDao().exists(job.getJobId())) {
        	return null;
        }

        String htmlSource = getHtmlContent(job.getUrl());
        if (htmlSource == null) {
            return null;
        }

        app.setAppId(job.getJobId());
        app.setAppName(job.getName());
        app.setAuthor(getAuthor(htmlSource));
        app.setFitType(getFitType(htmlSource));
        app.setSize(getSize(htmlSource));
        app.setReleaseDate(getReleaseDate(htmlSource));
        app.setRomType(getRomType(htmlSource));
        app.setStar(getStar(htmlSource));
        app.setDescription(getDescription(htmlSource));
        app.setIconUrl(getIconUrl(htmlSource));
        app.setPhotos(getPhotos(htmlSource, job.getJobId()));
        app.setUrl(job.getUrl());
        app.setDownloadUrl(getRedirectDownloadURL(getDownloadUrl(htmlSource)));
        app.setCategoryId((Integer) getCategoryIdAndName(htmlSource)[0]);
        app.setCategoryName((String) getCategoryIdAndName(htmlSource)[1]);
        app.setCompany(getCompany(htmlSource));
        
        // download
        String localIconUrl = RomImageDownloader.downloader(app.getIconUrl());
        app.setIconUrl(localIconUrl);
        
        List<RomPhoto> localPhotos = photoDownloader(app.getPhotos());
        app.setPhotos(localPhotos);
        
        return app;
    }
    
    public String getCompany(String htmlSource) {
    	String company = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathCompany);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                company = tNode.getText().toString().trim();
            }
        }
        
        return company;
    }
    
    public Object[] getCategoryIdAndName(String htmlSource) {
        Object[] categoryIdAndName = new Object[2];
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathCategory);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                String categoryUrl = tNode.getAttributeByName("href").trim();
                
                int categoryId = 0;
                String temp = categoryUrl.substring(categoryUrl.indexOf("cid-") + 4, categoryUrl.indexOf("-page"));
                if (!temp.trim().isEmpty()) {
                	categoryId = Integer.parseInt(temp);
                }
                categoryIdAndName[0] = categoryId;
                categoryIdAndName[1] = tNode.getText().toString().trim();
            }
        }
        
        return categoryIdAndName;
    }
    
    public String getDownloadUrl(String htmlSource) {
        String downloadUrl = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathDownloadUrl);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                downloadUrl = tNode.getAttributeByName("href").trim();
            }
        }
        
        return "http://www.shendu.com".concat(downloadUrl);
    }

    public List<RomPhoto> getPhotos(String htmlSource, int id) {
        List<RomPhoto> photos = new ArrayList<RomPhoto>();

        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathPhotos);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                String originalUrl = tNode.getAttributeByName("src").trim();

                RomPhoto photo = new RomPhoto();
                photo.setAppId(id);
                photo.setOriginalUrl(originalUrl);
                
                photos.add(photo);
            }
        }
        
        return photos;
    }

    public String getIconUrl(String htmlSource) {
        String iconUrl = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathIconUrl);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                iconUrl = tNode.getAttributeByName("src").trim();
            }
        }
        
        return iconUrl;
    }

    public String getDescription(String htmlSource) {
        String description = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathDescription);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                description = tNode.getText().toString().trim();
            }
        }
        
        return description;
    }

    public String getRomType(String htmlSource) {
        String romType = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathRomType);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                romType = tNode.getText().toString().trim();
                romType = romType.replace("安卓版本：", "");
            }
        }
        
        return romType;
    }
    
    public float getStar(String htmlSource) {
        float size = 0.0f;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathStar);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                String starStr = tNode.getAttributeByName("class").trim();
                starStr = starStr.replace("star_", "");
                size = Float.parseFloat(starStr);
            }
        }
        
        return size;
    }

    public String getReleaseDate(String htmlSource) {
        String releaseDate = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathReleaseDate);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                releaseDate = tNode.getText().toString().trim();
                releaseDate = releaseDate.replace("更新时间：", "");
            }
        }
        
        return releaseDate;
    }

    public float getSize(String htmlSource) {
        float size = 0.0f;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathSize);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                String sizeStr = tNode.getText().toString().trim();
                sizeStr = sizeStr.replace("ROM大小：", "");
                sizeStr = sizeStr.replace("MB", "");
                size = Float.parseFloat(sizeStr.trim());
            }
        }
        
        return size;
    }

    public String getFitType(String htmlSource) {
        String fitType = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathFitType);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                fitType = tNode.getText().toString().trim();
                fitType = fitType.replace("适合机型：", "");
            }
        }
        
        return fitType;
    }

    public String getAuthor(String htmlSource) {
        String author = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathAuthor);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                author = tNode.getText().toString().trim();
                author = author.replace("ROM作者：", "");
            }
        }
        
        return author;
    }
    
    public String getRedirectDownloadURL(String downloadURL) {
        String redirectDownloadURL = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        params.setParameter(ClientPNames.HANDLE_REDIRECTS, false); // 关闭GET 重定向
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        httpclient
                .setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(
                        3, true));
        try {
            HttpProtocolParams.setUserAgent(httpclient.getParams(),
                    BrowserUtil.getRandomBrowserUserAgent());
            HttpGet httpget = new HttpGet(downloadURL);
            HttpResponse response = httpclient.execute(httpget);
            int statuscode = response.getStatusLine().getStatusCode();
            if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
                    || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
                    || (statuscode == HttpStatus.SC_SEE_OTHER)
                    || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                Header header = response.getFirstHeader("Location");
                if (header != null) {
                    redirectDownloadURL = header.getValue().trim();
                }
            }
        } catch (Exception e) {
            log.info("failed to get redirectdownloadurl.");
        }
        return redirectDownloadURL;
    }

    private List<RomJob> getJobs(String htmlSource) {
        List<RomJob> jobs = new ArrayList<RomJob>();

        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] jobNodes = null;

        try {
            jobNodes = node.evaluateXPath(xpath);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }

        if (jobNodes != null && jobNodes.length > 0) {
            for (int index = 0; index < jobNodes.length; index++) {
                TagNode tNode = (TagNode) jobNodes[index];

                String name = tNode.getText().toString().trim();
                String url = tNode.getAttributeByName("href").trim();
                int jobId = Integer.parseInt(url.substring(url.indexOf("/rom-") + 5, url.indexOf(".html")));

                RomJob job = new RomJob();
                job.setJobId(jobId);
                job.setName(name);
                job.setUrl(url);
                
                jobs.add(job);
            }
        }
        return jobs;
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

    public String getHtmlContent(String url) {
        int retryCounter = 0;
        String htmlSource = null;

        do {
            htmlSource = URLFetchUtil.fetch(url);
            retryCounter++;
            if (retryCounter > 1) {
                log.info("retry connection: " + url);
            }
        }
        while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

        if (htmlSource == null || htmlSource.length() == 0 || htmlSource.contains("404 NOT FOUND")) {
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
}
