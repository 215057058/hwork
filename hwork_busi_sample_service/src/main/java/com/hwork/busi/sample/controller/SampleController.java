package com.hwork.busi.sample.controller;

import com.hwork.busi.sample.service.SampleService;
import com.hwork.frame.core.init.CustomPropertyConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lipz on 2017/4/25.
 */
@Api(value = "sample", tags = {"样例"})
@Controller
@RequestMapping(value = "/sample")
public class SampleController {
    @Autowired
    private SampleService sampleService;

    @ApiOperation(value = "返回首页")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "path")})
    @RequestMapping(value = "/home/{name}", method = RequestMethod.GET)
    public String home(@PathVariable String name, ModelMap modelMap) {
        modelMap.put("params", CustomPropertyConfigurer.getctxPropertiesMap());
        modelMap.put("pageMsg", name);
        modelMap.put("data", "somedata");
        return "sample/home";
    }
}
