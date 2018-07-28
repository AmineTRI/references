package org.leo.boot.data.repository;

import org.leo.boot.data.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

/**
 * Custom repository implementation
 * This class and its implemented interface are analog to a Service implementation in JPA
 * Here, in contrary to a JPA Service implementation, we don't Autowire to the repository but to MongoTemplate
 * @author fahdessid
 */
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    @Override
    public int setItemRank(String item, int rank) {
        Query query = new Query(Criteria.where("name").is(item));
        Update update = new Update();
        
        update.set("rank", rank);

        WriteResult result = mongoTemplate.updateFirst(query, update, Item.class);

        if (result != null) {
            return result.getN();
        } else {
            return 0;
        }
    } 

    @Autowired
    MongoTemplate mongoTemplate;
}
