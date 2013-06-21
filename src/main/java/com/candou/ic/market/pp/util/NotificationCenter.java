package com.candou.ic.market.pp.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.StringEntity;

import com.candou.util.URLFetchUtil;

public class NotificationCenter {

    public static final String BASE_URL = "http://jsondata.25pp.com/index.html";

    public static String notify(int type, int pn) {
        String content = null;
        try {
            switch (type) {
                case 1:
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("tunnel-command", "4261425200");
                    StringEntity httpEntity = new StringEntity(
                        "{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":"
                            + pn + "}");
                   content = URLFetchUtil.fetchPost(BASE_URL, httpEntity, headers);
                   break;
                case 2:
                    // TODO
                case 3:

            }
        } catch (Exception e) {
            //
        }
        return content;
    }

}
