package com.researchorum.mongodb;

import com.researchorum.crawler.PublicationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pbathala
 */
public class UrlContainer {

    private Map<PublicationType, List<String>> container = new HashMap<PublicationType, List<String>>();

    public List<String> getLinksByType(PublicationType type) {
        return container.get(type);
    }

    public void setLinksByType(PublicationType type, List<String> links) {
        container.put(type, links);
    }
}
