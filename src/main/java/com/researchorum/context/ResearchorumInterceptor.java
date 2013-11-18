package com.researchorum.context;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pbathala
 */
public class ResearchorumInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(ResearchorumInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Pre handling the request");
        ResearchorumContextFactory.create(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("Post handling the request");
        ResearchorumContextFactory.reset();
    }
}
