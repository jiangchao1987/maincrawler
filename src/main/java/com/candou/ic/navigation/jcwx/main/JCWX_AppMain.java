package com.candou.ic.navigation.jcwx.main;

import org.apache.log4j.Logger;

import com.candou.ic.navigation.jcwx.crawler.JCWX_AppCrawler;

/**
 * 精彩微信 App爬虫入口。
 *
 * @author jiangchao
 */
public class JCWX_AppMain {

    private static Logger log = Logger.getLogger(JCWX_AppMain.class);

    public static void main(String[] args) {
        while (true) {
            JCWX_AppCrawler crawler = new JCWX_AppCrawler();
            crawler.start();

            try {
                log.info("sleep...");
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
