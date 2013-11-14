package com.researchorum.services;

import com.researchorum.models.Record;
import com.researchorum.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pbathala
 */
@Repository
public class DefaultRecordService implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<Record> getRecordsByTitle(String title, int page, int itemsPerPage) {
        return recordRepository.getRecordsByTitle(title, page, itemsPerPage);
    }

    @Override
    public List<Record> getRecordsByTag(String tag, int page, int itemsPerPage) {
        return recordRepository.getRecordsByTag(tag, page, itemsPerPage);
    }
}
