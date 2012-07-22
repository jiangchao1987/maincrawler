package com.candou.ac.rom.crawler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RomCrawlerTest {
    private static String htmlContent = null;
    private static RomCrawler crawler = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        crawler = new RomCrawler();
        htmlContent = crawler.getHtmlContent("http://www.shendu.com/android/rom-635.html");
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

    @Test
    public void testGetFitType() {
        String fitType = crawler.getFitType(htmlContent);
        System.out.println("$" + fitType + "$");
    }

}
