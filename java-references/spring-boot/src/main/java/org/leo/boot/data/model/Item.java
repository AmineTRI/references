package org.leo.boot.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Item model class
 * @author fahdessid
 */
@Data
@Document(collection = "items_collection")
public class Item {

  public Item(String name, String code, String type, int quantity){
    this.name = name;
    this.code = code;
    this.type = type;
    this.quantity = quantity;
  }
  
  @Id
  private String id;
  private String name;
  private String code;
  private String type;
  private int quantity;
}
