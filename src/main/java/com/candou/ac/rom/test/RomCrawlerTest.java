package com.candou.ac.rom.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.candou.ac.rom.crawler.RomCrawler;

public class RomCrawlerTest {
    private static String htmlSource;
    private static RomCrawler crawler;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        crawler = new RomCrawler();
        htmlSource = crawler.getHtmlContent("http://www.shendu.com/android/rom-863.html");
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
    public void testGetStar() {
        System.out.println(crawler.getStar(htmlSource));
    }
    
    @Test
    public void testGetRedirectDownloadURL() {
        System.out.println(crawler.getRedirectDownloadURL("http://www.shendu.com/?c=download&a=index&source=rom&id=863"));
    }

}
