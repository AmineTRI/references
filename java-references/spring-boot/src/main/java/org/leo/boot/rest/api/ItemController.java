package org.leo.boot.rest.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.leo.boot.data.model.Item;
import org.leo.boot.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller for items CRUD operations
 * @author fahdessid
 */

@RestController
@RequestMapping("/item")
public class ItemController {

  @RequestMapping(method = RequestMethod.POST)
  public Map<String,Object> createBook(@RequestBody Map<String, Object> itemMap) {
    Item item = new Item(itemMap.get("name").toString(),
        itemMap.get("code").toString(),
        itemMap.get("type").toString(),
        Integer.parseInt(itemMap.get("quantity").toString()));

    itemRepository.save(item);

    Map<String,Object> response = new LinkedHashMap<String,Object>();

    response.put("message", "Item created successfully");
    response.put("item", item);

    return response;
  }

  @RequestMapping(method = RequestMethod.GET, value="/{itemId}")
  public Item getItemDetails(@PathVariable("itemId") String itemId) {
    return itemRepository.findOne(itemId);
  }

  @RequestMapping(method = RequestMethod.PUT, value="/{itemId}")
  public Map<String, Object> editItem(@PathVariable("itemId") String itemId, @RequestBody Map<String, Object> itemMap) {
    Item item = new Item(itemMap.get("name").toString(),
        itemMap.get("code").toString(),
        itemMap.get("type").toString(),
        Integer.parseInt(itemMap.get("quantity").toString()));

    item.setId(itemId);

    Map<String, Object> response = new LinkedHashMap<String, Object>();

    response.put("message", "Item Updated successfully");
    response.put("item", itemRepository.save(item));

    return response;
  }

  @RequestMapping(method = RequestMethod.DELETE, value="/{itemId}")
  public Map<String, String> deleteBook(@PathVariable("itemId") String itemId) {
    itemRepository.delete(itemId);

    Map<String, String> response = new HashMap<String, String>();
    response.put("message", "Item deleted successfully");

    return response;
  }

  @RequestMapping(method = RequestMethod.GET)
  public Map<String,Object> getAllBooks() {
    List<Item> items = itemRepository.findAll();
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    
    response.put("total Items", items.size());
    response.put("items", items);
    
    return response;
  }
  
  @RequestMapping(method = RequestMethod.PUT, value="/{itemId}/{rank}")
  public Map<String, String> setRanking(@PathVariable("itemId") String itemId, @PathVariable("rank") int rank) {
    int result = itemRepository.setItemRank(itemId, rank);
    
    Map<String,String> response = new HashMap<String,String>();
    response.put("message", "Item " + itemId + " is now ranked " + rank);
    response.put("result", Integer.toString(result));
    
    return response;
  }

  @Autowired
  private ItemRepository itemRepository;
}
