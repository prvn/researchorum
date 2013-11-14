package com.researchorum.context;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pbathala
 */
public class ResearchorumContext {

    private static final Logger logger = Logger.getLogger(ResearchorumContext.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private SearchContext searchContext;

    public ResearchorumContext(final HttpServletRequest request, final HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public SearchContext getSearchContext() {
        return searchContext;
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }
}
