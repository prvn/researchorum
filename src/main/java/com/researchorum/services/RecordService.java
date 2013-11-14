package com.researchorum.services;

import com.researchorum.models.Record;

import java.util.List;

/**
 * @author pbathala
 */
public interface RecordService {

    List<Record> getRecordsByTitle(String title, int page, int itemsPerPage);

    List<Record> getRecordsByTag(String tag, int page, int itemsPerPage);
}
