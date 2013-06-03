package com.candou.ic.navigation.wxdh.main;

import org.apache.log4j.Logger;

import com.candou.ic.navigation.wxdh.crawler.WXDH_AppCrawler;

/**
 * @author aifeng.liu
 *
 */
public class WXDH_AppMain {
    private static Logger log = Logger.getLogger(WXDH_AppMain.class);

    public static void main(String[] args) {
        while (true) {
            WXDH_AppCrawler crawler = new WXDH_AppCrawler();
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
