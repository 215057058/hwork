package com.hwork.frame.core.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class StringUtil {
	
	private static final Log logger = LogFactory.getLog(StringUtil.class);
	public static String getWebInfoPath() {
        String separator = File.separator;// System.getProperty("file.separator")+"";
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        path = URLDecoder.decode(path);
        logger.debug("期望获取系统路径" + getAppPath(Thread.currentThread().getContextClassLoader().getResource("")));
        path = path.replace("/", separator); // 将/换成\
        path = path.replace("file:", ""); // 去掉file:
        path = path.replace("classes" + separator, ""); // 去掉class\
        if (separator.equals("\\")) {
            path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
        }

        return path;
    }
	
	private static String getAppPath(URL r) {
        String filePath = r.getFile();
        String result = new File(new File(filePath).getParent()).getParent();

        if (!filePath.contains("WEB-INF")) {
            // Assume we need to add the "WebContent" folder if using Jetty.
            result = FilenameUtils.concat(result, "WebContent");
        }

        return result;
    }
}
