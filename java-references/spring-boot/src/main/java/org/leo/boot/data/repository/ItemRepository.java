package org.leo.boot.data.repository;

import org.leo.boot.data.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Item repository interface to communicate with MongoDB
 * @author fahdessid
 */
public interface ItemRepository extends MongoRepository<Item, String>, ItemRepositoryCustom {

}
