package com.researchorum.services;

import com.researchorum.models.SearchPageModel;

/**
 * @author pbathala
 */
public interface RecordService {

    SearchPageModel getRecordsByTitle(String title);

    SearchPageModel getRecordsByTitle(String title, int page, int itemsPerPage);

    SearchPageModel getRecordsByTag(String tag);

    SearchPageModel getRecordsByTag(String tag, int page, int itemsPerPage);
}
