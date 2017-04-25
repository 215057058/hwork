package com.hwork.frame.mybatis.cfg;

import com.alibaba.druid.pool.DruidDataSource;
import com.hwork.frame.core.init.CustomPropertyConfigurer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DynamicDataSourceDruid extends AbstractRoutingDataSource implements ApplicationContextAware {

    private static final Log logger = LogFactory.getLog(DynamicDataSourceDruid.class);
    public static String DEFAULT_DATASOURCE = "ds.sys";
    private static Map<Object, Object> tds = new HashMap<Object, Object>();

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String ds = DataSourceContextHolder.getDbType();
        if (ds == null || ds.equals("")) {
            ds = DEFAULT_DATASOURCE;
        }
        return ds;
    }

    @Override
    public void afterPropertiesSet() {
        // 获取数据库数据源配置信息
        Map<String, DataSourceInfo> map = getDataSources(
                (String) CustomPropertyConfigurer.getConfigProperty("initParameter"));
        try {
            addSourcesToApp(map);
            setTargetDataSources(tds);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		addSourceBeanToApp((String) CustomPropertyConfigurer.getConfigProperty("ds.sys.dsName"));
        super.afterPropertiesSet();

    }
    private void addSourcesToApp(Map<String, DataSourceInfo> map) throws SQLException {
        Iterator<Map.Entry<String, DataSourceInfo>> entries  = map.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, DataSourceInfo> entry = entries.next();
            DataSourceInfo dsInfo = entry.getValue();
            DruidDataSource ds = new DruidDataSource();
            ds.setDriverClassName(dsInfo.getDsDriverName());
            ds.setUrl(dsInfo.getDsUrl());
            ds.setUsername(dsInfo.getUsername());
            ds.setPassword(dsInfo.getPassword());
            ds.setFilters((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.filters"));
            ds.setMaxActive(Integer.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.maxActive")));
            ds.setInitialSize(Integer.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.initialSize")));
            ds.setMaxWait(Long.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.maxWait")));
            ds.setMinIdle(Integer.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.minIdle")));
            ds.setTimeBetweenEvictionRunsMillis(Long.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.timeBetweenEvictionRunsMillis")));
            ds.setMinEvictableIdleTimeMillis(Long.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.minEvictableIdleTimeMillis")));
            ds.setValidationQuery((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.validationQuery"));
            ds.setTestWhileIdle(Boolean.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.testWhileIdle")));
            ds.setTestOnBorrow(Boolean.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.testOnBorrow")));
            ds.setTestOnReturn(Boolean.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.testOnReturn")));
            ds.setMaxOpenPreparedStatements(Integer.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.maxOpenPreparedStatements")));
            ds.setRemoveAbandoned(Boolean.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.removeAbandoned")));
            ds.setRemoveAbandonedTimeout(Integer.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.removeAbandonedTimeout")));
            ds.setLogAbandoned(Boolean.valueOf((String)CustomPropertyConfigurer.getConfigProperty("ds.sys.logAbandoned")));
            tds.put(entry.getKey(), ds);
        }
    }


    private Map<String, DataSourceInfo> getDataSources(String configProperty) {
        // TODO Auto-generated method stub
        Map<String, DataSourceInfo> map = new HashMap<String, DataSourceInfo>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName((String) CustomPropertyConfigurer.getConfigProperty("ds.sys.dbDriverClass"));
            conn = DriverManager.getConnection((String) CustomPropertyConfigurer.getConfigProperty("ds.sys.dbUrl"),
                    (String) CustomPropertyConfigurer.getConfigProperty("ds.sys.dbUserName"),
                    (String) CustomPropertyConfigurer.getConfigProperty("ds.sys.dbPwd"));
            ps = conn.prepareStatement(
                    "select uuid,ds_code,ds_url,ds_driverclass,ds_username,ds_password,ds_runtype from sys_datasource where ds_runtype=? and ds_enable='1'");
            ps.setString(1, configProperty);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("ds_code"),
                        new DataSourceInfo(rs.getString("uuid"), rs.getString("ds_code"), rs.getString("ds_url"),
                                rs.getString("ds_driverclass"), rs.getString("ds_username"),
                                rs.getString("ds_password"), rs.getString("ds_runtype")));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return map;
    }

    private void addSourceBeanToApp(String dsId) {
        // logger.debug("start1****************************************"+((Map<String,
        // Object>) SpringUtils.getBean("targetDataSources")).size());
        DefaultListableBeanFactory dcf = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        // BeanDefinition definition;
        // definition = new ChildBeanDefinition(DEFAULT_DATASOURCE);
        // dcf.registerBeanDefinition(dsId,definition);
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName((String) CustomPropertyConfigurer.getConfigProperty("ds.sys.dbDriverClass"));
        ds.setUrl((String) CustomPropertyConfigurer.getConfigProperty("ds.sys.dbUrl"));
        ds.setUsername("hwork2");
        ds.setPassword("hwork2");
        tds.put("hwork2", ds);
        setTargetDataSources(tds);
    }

}
