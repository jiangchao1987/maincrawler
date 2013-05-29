package com.candou.ac.rom.main;

import com.candou.ac.rom.crawler.RomCrawler;

public class RomMain {

    /**
     * 抓取app_downloadUrl
     * @param args
     */
    public static void main(String[] args) {
        RomCrawler crawler = new RomCrawler();
        crawler.start();
    }
}
