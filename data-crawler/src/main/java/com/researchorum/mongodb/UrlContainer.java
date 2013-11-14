package com.researchorum.mongodb;

import java.util.List;

/**
 * @author pbathala
 */
public class UrlContainer {

    private List<String> acmUrls;
    private List<String> ieeeUrls;

    public List<String> getAcmUrls() {
        return acmUrls;
    }

    public void setAcmUrls(List<String> acmUrls) {
        this.acmUrls = acmUrls;
    }

    public List<String> getIeeeUrls() {
        return ieeeUrls;
    }

    public void setIeeeUrls(List<String> ieeeUrls) {
        this.ieeeUrls = ieeeUrls;
    }
}
