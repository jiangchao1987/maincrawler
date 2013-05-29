package com.candou.ic.navigation.jcwx.main;

import org.apache.log4j.Logger;

import com.candou.ic.navigation.jcwx.crawler.JCWX_JobCrawler;

/**
 * 精彩微信 爬虫入口。
 *
 * @author jiangchao
 */
public class JCWX_JobMain {

    private static Logger log = Logger.getLogger(JCWX_JobMain.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
            return;
        }

        String seedFile = args[0];

        while (true) {
            JCWX_JobCrawler crawler = new JCWX_JobCrawler(seedFile);
            crawler.start();

            try {
                log.info("sleep...");
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void usage() {
        System.err.println("Usage: java JCWX_JobMain <seed file>");
        System.exit(1);
    }

}
