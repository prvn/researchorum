package com.researchorum.url;

import com.researchorum.context.ResearchorumContextFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author pbathala
 */
public class DefaultUrlBuilder implements UrlBuilder {

    private static final Logger logger = Logger.getLogger(DefaultUrlBuilder.class);

    private String url;
    private Map<String, String[]> urlParams;

    protected DefaultUrlBuilder() {
        HttpServletRequest request = ResearchorumContextFactory.getContext().getRequest();
        StringBuffer sb = request.getRequestURL();

        this.url = sb.toString();
        this.urlParams = request.getParameterMap();
    }

    public static UrlBuilder newInstance() {
        UrlBuilder urlBuilder = new DefaultUrlBuilder();
        return urlBuilder;
    }

    @Override
    public String getUrl() {
        StringBuffer sb = new StringBuffer();
        String seperator = "";
        for (Map.Entry entry : urlParams.entrySet()) {
            sb.append(seperator);
            seperator = "&";
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        url += sb.toString();
        return url;
    }

    @Override
    public void addParameter(String key, String value) {
        urlParams.put(key, new String[]{value});
    }

    @Override
    public void addAllParameter(Map<String, String> parameters) {
        for (Map.Entry entry : parameters.entrySet()) {
            addParameter(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    @Override
    public void removeParameter(String key) {
        urlParams.remove(key);
    }

    @Override
    public void removeAllParameters(List<String> keys) {
        for (String key : keys) {
            removeParameter(key);
        }
    }
}
