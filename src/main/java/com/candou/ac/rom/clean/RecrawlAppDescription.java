package com.candou.ac.rom.clean;

import java.util.List;

import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.candou.ac.rom.bean.RomApp;
import com.candou.ac.rom.dao.DaoFactory;
import com.candou.util.URLFetchUtil;

public class RecrawlAppDescription {
	private static Logger log = Logger.getLogger(RecrawlAppDescription.class);
	private static HtmlCleaner cleaner;
    private static final int retryNumber = 5;
    private static String xpathDescription = "//div[@class='jieshao_normal']";

	public static void main(String[] args) {
		List<RomApp> apps = findApps();
		
		for (RomApp app : apps) {
			log.info(String.format("fetch [%s]", app.getUrl()));

            String htmlSource = getHtmlContent(app.getUrl());
            if (htmlSource == null) {
                continue;
            }
            
            app.setDescription(getDescription(htmlSource));
            
            updateDescription(app);
		}
	}
	
	private static void updateDescription(RomApp app) {
		DaoFactory.getRomAppDao().updateDescription(app);
	}

	private static List<RomApp> findApps() {
		List<RomApp> apps = DaoFactory.getRomAppDao().findAllApps();
    	return apps;
	}
	
	public static String getDescription(String htmlSource) {
        String description = null;
        
        HtmlCleaner cleaner = getCleaner();
        TagNode node = cleaner.clean(htmlSource);
        Object[] nodes = null;

        try {
            nodes = node.evaluateXPath(xpathDescription);
        }
        catch (XPatherException e) {
            e.printStackTrace();
        }
        
        if (nodes != null && nodes.length > 0) {
            for (int index = 0; index < nodes.length; index++) {
                TagNode tNode = (TagNode) nodes[index];

                description = tNode.getText().toString().trim();
            }
        }
        
        return description;
    }
	
	public static HtmlCleaner getCleaner() {
        if (cleaner == null) {
            cleaner = new HtmlCleaner();
            CleanerProperties props = cleaner.getProperties();
            props.setAllowHtmlInsideAttributes(true);
            props.setAllowMultiWordAttributes(true);
            props.setRecognizeUnicodeChars(true);
            props.setRecognizeUnicodeChars(true);
            props.setTranslateSpecialEntities(true);
            props.setAdvancedXmlEscape(true);
            props.setOmitComments(true);
        }
        return cleaner;
    }
	
	public static String getHtmlContent(String url) {
        int retryCounter = 0;
        String htmlSource = null;

        do {
            htmlSource = URLFetchUtil.fetchGet(url);
            retryCounter++;
            if (retryCounter > 1) {
                log.info("retry connection: " + url);
            }
        }
        while (retryCounter < retryNumber && (null == htmlSource || htmlSource.length() == 0));

        if (htmlSource == null || htmlSource.length() == 0 || htmlSource.contains("404 NOT FOUND")) {
            return null;
        }

        return htmlSource;
    }

}
