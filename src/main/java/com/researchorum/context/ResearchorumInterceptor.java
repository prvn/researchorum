package com.researchorum.context;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pbathala
 */
public class ResearchorumInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(ResearchorumInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //logger.info("Pre handle");
        ResearchorumContextFactory.create(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //logger.info("Post handle");
        ResearchorumContextFactory.reset();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //logger.info("after completion");
    }
}
