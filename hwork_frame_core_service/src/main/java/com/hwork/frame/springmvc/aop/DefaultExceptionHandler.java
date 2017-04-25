package com.hwork.frame.springmvc.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class DefaultExceptionHandler implements HandlerExceptionResolver{

	private static final Log logger = LogFactory.getLog(DefaultExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();  
        attributes.put("code", "1000001");  
        attributes.put("msg", ex.getMessage());  
        view.setAttributesMap(attributes);  
        mv.setView(view);   
        logger.debug("异常:" + ex.getMessage(), ex);  
        return mv;  
	}

}
