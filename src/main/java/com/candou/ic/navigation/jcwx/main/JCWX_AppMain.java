package com.candou.ic.navigation.jcwx.main;

import org.apache.log4j.Logger;

public class JCWX_AppMain {

    private static Logger log = Logger.getLogger(JCWX_AppMain.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        while(true){
            try {
                log.info("sleep...");
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
