package com.researchorum.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pbathala
 */
public class UrlContainer {

    public static final String ACM_LINKS = "acm_links";
    public static final String IEEE_LINKS = "ieee_links";

    private Map<String, List<String>> container = new HashMap<String, List<String>>();

    public List<String> getAcmUrls() {
        return container.get(ACM_LINKS);
    }

    public void setAcmUrls(List<String> acmUrls) {
        List<String> links = container.get(ACM_LINKS);
        if (links == null) {
            links = new ArrayList<String>();
            container.put(ACM_LINKS, links);
        }
        links.addAll(acmUrls);
    }

    public List<String> getIeeeUrls() {
        return container.get(IEEE_LINKS);
    }

    public void setIeeeUrls(List<String> ieeeUrls) {
        List<String> links = container.get(IEEE_LINKS);
        if (links == null) {
            links = new ArrayList<String>();
            container.put(IEEE_LINKS, links);
        }
        links.addAll(ieeeUrls);
    }
}
