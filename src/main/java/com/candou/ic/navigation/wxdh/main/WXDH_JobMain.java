package com.candou.ic.navigation.wxdh.main;

import org.apache.log4j.Logger;

import com.candou.ic.navigation.wxdh.crawler.WXDH_JobCrawler;


/**
 * @author aifeng.liu
 *
 */
public class WXDH_JobMain {

    private static Logger log = Logger.getLogger(WXDH_JobMain.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
            return;
        }

        String seedFile = args[0];

        while (true) {
            WXDH_JobCrawler crawler = new WXDH_JobCrawler(seedFile);
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
        System.err.println("Usage: java WXDH_JobMain <seed file>");
        System.exit(1);
    }
}
