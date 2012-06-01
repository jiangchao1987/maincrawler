package com.candou.ac.rom.main;

import com.candou.ac.rom.crawler.DownloadCrawler;

public class DownloadMain {

    public static void main(String[] args) {
        DownloadCrawler crawler = new DownloadCrawler();
        crawler.start();
    }
}
