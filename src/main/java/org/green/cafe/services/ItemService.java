package org.green.cafe.services;

import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import org.green.cafe.exceptions.ValidationException;
import org.green.cafe.models.Employee;
import org.green.cafe.models.Item;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.ItemRequest;
import org.green.cafe.models.dto.ItemResponse;
import org.green.cafe.util.FormatUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ItemService {

  @Inject
  EntityManager entityManager;

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public Item itemPersist(String itemId, ItemRequest itemRequest, User user){
    Optional<Item> optionalItem = Item.findByIdOptional(itemId);

    Item item;

    if(optionalItem.isEmpty()){
      item = new Item();
      item.setCreatedBy(user);
      item.setUpdatedBy(user);
    }else{
      item = optionalItem.get();
      item.setUpdatedBy(user);
    }

    item.setName(itemRequest.name);
    item.setDescription(itemRequest.description);
    item.setCategory(itemRequest.category);
    item.setPrice(itemRequest.price);
    item.persist();
    return item;
  }

  public Response post(String userId, ItemRequest itemRequest){
    if(!FormatUtil.isAlphabet(itemRequest.name)){
      throw new ValidationException("INVALID_REQUEST_ITEM");
    }

    Optional<User> userOptional = User.findByIdOptional(userId);
    if (userOptional.isEmpty()) {
      throw new ValidationException("USER_NOT_FOUND_FROM_CREATE_ITEM_CATEGORY");
    }

    itemPersist("",itemRequest,userOptional.get());

    return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
  }

  public Response put(String userId, String itemId, ItemRequest itemRequest){
    if(!FormatUtil.isAlphabet(itemRequest.name)){
      throw new ValidationException("INVALID_REQUEST_ITEM");
    }

    Optional<User> userOptional = User.findByIdOptional(userId);
    if (userOptional.isEmpty()) {
      throw new ValidationException("USER_NOT_FOUND_FROM_CREATE_ITEM_CATEGORY");
    }

    itemPersist(itemId,itemRequest,userOptional.get());
    return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
  }

  public Response delete(String userId, String itemId){
    deleteItem(userId,itemId);
    return Response.ok(new HashMap<>()).build();
  }

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public void deleteItem(String userId, String itemId){
    Optional<User> userOptional = User.findByIdOptional(userId);
    if (userOptional.isEmpty()) {
      throw new ValidationException("USER_NOT_FOUND_FROM_DELETE_ITEM");
    }
    Optional<Item> itemOptional = Item.findByIdOptional(itemId);
    if(itemOptional.isEmpty()){
      throw new ValidationException("ITEM_NOT_FOUND");
    }

    itemOptional.get().delete();
  }

  public Response detailItem(String itemId){
    Item item = Item.findById(itemId);
    if(item == null){
      throw new ValidationException("ITEM_NOT_FOUND");
    }
    return Response.ok(item.list("id = ?1", itemId)).build();
  }

  public Response list(Integer page, String category){
    if (page < 1) {
      throw new ValidationException("BAD_REQUEST");
    }

    Long countItem = Item.countByCategory(category);
    Double totalPage = Math.ceil(countItem/10.0);

    StringBuilder stringBuilder = new StringBuilder("SELECT * FROM main.item");

    Integer offset = ((page-1)*10);

    Query query = entityManager.createNativeQuery(stringBuilder.toString(), Item.class);
    if (category != null) {
      stringBuilder.append("WHERE category = :categ");
      query.setParameter("categ", category);
    }
    query.setMaxResults(10);
    query.setFirstResult(offset);

    List<Item> list = query.getResultList();

    ItemResponse itemResponse = new ItemResponse();
    itemResponse.totalPage = totalPage.longValue();
    itemResponse.dataList = list;

    return Response.ok(itemResponse).build();
  }
}
