package com.researchorum.services;

import com.researchorum.context.ResearchorumContextFactory;
import com.researchorum.models.Record;
import com.researchorum.models.SearchPageModel;
import com.researchorum.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author pbathala
 */
@Repository
public class DefaultRecordService implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public SearchPageModel getRecordsByTitle(String title) {
        int itemsPerPage = getItemsPerPage();
        int page = getPage();
        List<Record> records = getRecords(title, true, page, itemsPerPage);

        SearchPageModel searchPageModel = getSearchPageModel(records, title, true, itemsPerPage, page);

        return searchPageModel;
    }

    @Override
    public SearchPageModel getRecordsByTitle(String title, int page, int itemsPerPage) {
        List<Record> records = getRecords(title, true, page, itemsPerPage);

        SearchPageModel searchPageModel = getSearchPageModel(records, title, true, itemsPerPage, page);

        return searchPageModel;
    }

    @Override
    public SearchPageModel getRecordsByTag(String tag) {
        int itemsPerPage = getItemsPerPage();
        int page = getPage();

        List<Record> records = getRecords(tag, false, page, itemsPerPage);

        SearchPageModel searchPageModel = getSearchPageModel(records, tag, false, itemsPerPage, page);

        return searchPageModel;
    }

    @Override
    public SearchPageModel getRecordsByTag(String tag, int page, int itemsPerPage) {
        List<Record> records = getRecords(tag, false, page, itemsPerPage);

        SearchPageModel searchPageModel = getSearchPageModel(records, tag, false, itemsPerPage, page);

        return searchPageModel;
    }

    private List<Record> getRecords(String keyword, boolean isTitle, int page, int itemsPerPage) {
        if (isTitle) {
            return recordRepository.getRecordsByTitle(keyword, page, itemsPerPage);
        } else {
            return recordRepository.getRecordsByTag(keyword, page, itemsPerPage);
        }
    }

    private SearchPageModel getSearchPageModel(List<Record> records, String title, boolean isTitle, int itemsPerPage, int page) {
        SearchPageModel searchPageModel = new SearchPageModel();
        searchPageModel.setRecords(records);
        searchPageModel.setItemsPerPage(itemsPerPage);
        searchPageModel.setPage(page);
        searchPageModel.setKeyword(title);

        return searchPageModel;
    }

    private Map<String, String[]> getUrlParams() {
        return ResearchorumContextFactory.getContext().getUrlParams();
    }

    private int getItemsPerPage() {
        Map<String, String[]> urlParams = getUrlParams();

        // Get items per page
        int itemsPerPage = ItemPerPage.SMALL;
        String[] ippParams = urlParams.get("ipp");
        if (ippParams != null && ippParams.length > 0) {
            itemsPerPage = Integer.parseInt(ippParams[0]);
        }

        return itemsPerPage;
    }

    private int getPage() {
        Map<String, String[]> urlParams = getUrlParams();

        // Get page number
        int page = 1;
        String[] pageParams = urlParams.get("pg");
        if (pageParams != null && pageParams.length > 0) {
            page = Integer.parseInt(pageParams[0]);
        }

        return page;
    }
}
