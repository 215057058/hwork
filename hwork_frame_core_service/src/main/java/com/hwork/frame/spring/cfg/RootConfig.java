package com.hwork.frame.spring.cfg;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;

/**
 * Created by lipz on 2017/4/20.
 */
@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:applicationContext-sysconfig.xml")
@ComponentScan(basePackages = {"com.hwork.*"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)
})
public class RootConfig {

}
