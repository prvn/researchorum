package com.researchorum.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pbathala
 */
public class PaginationUtil {

    public static List<String> getPaginationUrls(String baseUrl, int pages, int itemsPerPage) {
        List<String> urls = new ArrayList<String>();
        StringBuilder sb = new StringBuilder(baseUrl);
        for (int i = 0; i < pages; i++) {
            sb.append("&");
        }
        return null;
    }
}
