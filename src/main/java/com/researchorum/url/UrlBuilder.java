package com.researchorum.url;

import java.util.List;
import java.util.Map;

/**
 * @author pbathala
 */
public interface UrlBuilder {

    String getUrl();

    void addParameter(String key, String value);

    void addAllParameter(Map<String, String> parameters);

    void removeParameter(String key);

    void removeAllParameters(List<String> keys);
}
