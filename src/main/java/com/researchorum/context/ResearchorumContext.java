package com.researchorum.context;

import com.researchorum.search.SearchContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pbathala
 */
public class ResearchorumContext {

    private static final Logger logger = Logger.getLogger(ResearchorumContext.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private SearchContext searchContext;
    private Locale locale;
    private Map<String, String[]> urlParams;

    public ResearchorumContext(final HttpServletRequest request, final HttpServletResponse response) {
        this.request = request;
        this.response = response;
        //this.urlParams = new ConcurrentHashMap<String, String[]>();

        initialize();
    }

    private void initialize() {
        logger.debug("Initializing researchorum context");
        Map<String, String[]> parameterMap = request.getParameterMap();
        this.urlParams = new ConcurrentHashMap<String, String[]>(parameterMap);
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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<String, String[]> getUrlParams() {
        return urlParams;
    }

//    public void setUrlParam(String key, Object value) {
//        this.urlParams.put(key, value);
//    }
}

