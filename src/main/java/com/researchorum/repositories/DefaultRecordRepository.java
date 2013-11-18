package com.researchorum.repositories;

import com.researchorum.models.Record;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author pbathala
 */
@Repository
public class DefaultRecordRepository implements RecordRepository {

    private static final Logger logger = Logger.getLogger(DefaultRecordRepository.class);

    private static final String POSTS_COLLECTION = "records";

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
        Pattern titlePattern = Pattern.compile(".*" + title + ".*", Pattern.CASE_INSENSITIVE);
        Query query = query(Criteria.where(Record.TITLE_FIELD).regex(titlePattern)).limit(itemsPerPage);
        logger.debug("Query: " + query.toString());
        if (page > 1) {
            query.skip(page * itemsPerPage);
        }
        List<Record> results = mongoOperations.find(query, Record.class);
        new PageImpl<Record>(results);
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
