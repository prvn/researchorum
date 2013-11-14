package com.researchorum.repositories;

import com.researchorum.models.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author pbathala
 */
@Repository
public class DefaultRecordRepository implements RecordRepository {

    private static final String POSTS_COLLECTION = "posts";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Record getRecord(String recordId) {
        return null;
    }

    @Override
    public List<Record> getRecordsByTitle(String title, int page, int itemsPerPage) {
        Pageable pageable = new PageRequest(page, itemsPerPage);
        MongoOperations mongoOperations = (MongoOperations) mongoTemplate;
        Query query = null;
        if (page > 1) {
            query = query(Criteria.where("title").regex(title, "i")).limit(itemsPerPage).skip(page * itemsPerPage);
        } else {
            query = query(Criteria.where("title").regex(title, "i")).limit(itemsPerPage);
        }
        List<Record> results = mongoOperations.find(query, Record.class);
        return results;
    }

    @Override
    public List<Record> getRecordsByTag(String tag, int page, int itemsPerPage) {
        Pageable pageable = new PageRequest(page, itemsPerPage);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Record saveRecord(Record record) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
