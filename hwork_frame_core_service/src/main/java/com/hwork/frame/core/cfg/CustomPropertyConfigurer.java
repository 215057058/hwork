package com.hwork.frame.core.cfg;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hwork.frame.core.util.DesUtils;
import com.hwork.frame.core.util.StringUtil;

/**
 * 自定义属性文件加载类
 *
 * @author lipz
 *
 */
public class CustomPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Log log = LogFactory.getLog(CustomPropertyConfigurer.class);

    private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

    private static String SUFFIX = "_md";

    /**
     * 根据路径和文件名，获取初始化配置信息
     *
     * @param initParameter
     * @param filePath
     */
    public static void loadParams(String initParameter) {
        ctxPropertiesMap.put("initParameter", initParameter);
        initParameter = "sysconfig-" + initParameter + ".properties";
        Properties properties = new Properties();
        try {
            InputStream in = null;
            in = new FileInputStream(StringUtil.getWebInfoPath() + initParameter);
            properties.load(in);
            log.info("============properties size " + (properties != null ? properties.size() : null));
            for (Object key : properties.keySet()) {
                String keyStr = key.toString();
                String value = properties.getProperty(keyStr).trim();
                value = desDecrypt(keyStr, value, properties);
                ctxPropertiesMap.put(keyStr, value);
            }
        } catch (Exception e) {
        }
    }

    private static String desDecrypt(String keyStr, String value, Properties props) {
        if (value.endsWith(SUFFIX)) {
            try {
                value = DesUtils.decrypt(value.replace(SUFFIX, ""));
                props.setProperty(keyStr, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            value = desDecrypt(keyStr, value, props);
            ctxPropertiesMap.put(keyStr, value);
        }
        super.processProperties(beanFactoryToProcess, props);
    }

    public static Object getConfigProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public static Map<String, Object> getctxPropertiesMap() {
        return ctxPropertiesMap;
    }
}
