package org.leo.boot.data.repository;

/**
 * Custom item repository to have custom methods
 * this interface needs an implementation with the same name as the main repository (ItemRepository) plus Impl
 * this interface needs to be extended by the main repository interface (the one extending the MongoRepository)
 * this Interface and it's implementation ares analog to a Service implementation in JPA
 * @author fahdessid
 */
public interface ItemRepositoryCustom {
  
  int setItemRank(String item, int rank);
}
