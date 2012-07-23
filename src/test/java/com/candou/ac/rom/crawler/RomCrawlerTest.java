package com.candou.ac.rom.crawler;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.candou.ac.rom.bean.RomPhoto;

public class RomCrawlerTest {
    private static String htmlContent = null;
    private static RomCrawler crawler = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        crawler = new RomCrawler();
        htmlContent = crawler.getHtmlContent("http://www.shendu.com/android/rom-803.html");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    public void testGetFitType() {
        String fitType = crawler.getFitType(htmlContent);
        System.out.println("$" + fitType + "$");
    }
    
    @Ignore
    public void testGetAuthor() {
    	String author = crawler.getAuthor(htmlContent);
    	System.out.println("$" + author + "$");
    }
    
    @Ignore
    public void testGetSize() {
    	float size = crawler.getSize(htmlContent);
    	System.out.println("$" + size + "$");
    }
    
    @Ignore
    public void testGetReleaseDate() {
    	String releaseDate = crawler.getReleaseDate(htmlContent);
    	System.out.println("$" + releaseDate + "$");
    }
    
    @Ignore
    public void testGetRomType() {
    	String romType = crawler.getRomType(htmlContent);
    	System.out.println("$" + romType + "$");
    }
    
    @Ignore
    public void testGetStar() {
    	float star = crawler.getStar(htmlContent);
    	System.out.println("$" + star + "$");
    }
    
    @Ignore
    public void testGetDescription() {
    	String description = crawler.getDescription(htmlContent);
    	System.out.println("$" + description + "$");
    }
    
    @Ignore
    public void testGetIconUrl() {
    	String iconUrl = crawler.getIconUrl(htmlContent);
    	System.out.println("$" + iconUrl + "$");
    }
    
    @Ignore
    public void testGetPhotos() {
    	List<RomPhoto> photos = crawler.getPhotos(htmlContent, 635);
    	for (RomPhoto photo : photos) {
    		System.out.println(photo.getOriginalUrl());
    	}
    }
    
    @Ignore
    public void testGetCategoryIdAndName() {
    	Object[] categoryIdAndNameObjects = crawler.getCategoryIdAndName(htmlContent);
    	System.out.println(categoryIdAndNameObjects[0] + "," + categoryIdAndNameObjects[1]);
    }
    
    @Ignore
    public void testGetDownloadUrl() {
    	String downloadUrl = crawler.getDownloadUrl(htmlContent);
    	System.out.println(downloadUrl);
    }
    
    @Ignore
    public void testGetRedirectDownloadURL() {
        System.out.println(crawler.getRedirectDownloadURL("http://www.shendu.com/?c=download&a=index&source=rom&id=863"));
    }
    
    @Test
    public void testGetCompany() {
    	System.out.println(crawler.getCompany(htmlContent));
    }

}
