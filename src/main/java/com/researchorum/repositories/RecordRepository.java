package com.researchorum.repositories;

import com.researchorum.models.Record;

import java.util.List;

/**
 * @author pbathala
 */
public interface RecordRepository {

    Record getRecord(String recordId);

    List<Record> getRecordsByTitle(String title, int page, int itemsPerPage);

    List<Record> getRecordsByTag(String tag, int page, int itemsPerPage);

    Record saveRecord(Record record);
}
