package com.hwork.frame.mybatis.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSourceContextHolder {

    private static final Log logger = LogFactory.getLog(DataSourceContextHolder.class);
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }
    public static String getDbType() {
        String dbType = ((String) contextHolder.get());
        return dbType;
    }
    public static void clearDbType() {
        contextHolder.remove();
    }
    public static void resetDbType() {
        setDbType(DynamicDataSourceDruid.DEFAULT_DATASOURCE);
    }


}
