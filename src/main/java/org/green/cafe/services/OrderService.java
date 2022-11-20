package org.green.cafe.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import io.vertx.core.json.JsonObject;
import org.green.cafe.exceptions.ValidationException;
import org.green.cafe.models.*;
import org.green.cafe.models.dto.requests.OrderDataRequest;
import org.green.cafe.models.dto.requests.OrderItemRequest;
import org.green.cafe.models.dto.requests.OrderRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
public class OrderService {
  public Response post(String userId, OrderRequest orderRequest) {
    Optional<User>  userOptional = User.findByIdOptional(userId);
    if(userOptional.isEmpty()){
      throw new ValidationException("USER_NOT_FOUND_INVALID_REQUEST");
    }

    OrderDataRequest orderData = orderRequest.orderData;
    List<OrderItemRequest> orderItem = orderRequest.orderItem;

    Optional<PaymentType> paymentTypeId = PaymentType.findByIdOptional(orderData.paymentTypeId);
    if(paymentTypeId.isEmpty()){
      throw new ValidationException("NOT_FOUND_PAYMENT_TYPE");
    }

    Optional<Employee> employeeCashier = Employee.find("id = ?1", orderData.cashierId).firstResultOptional();
    if(employeeCashier.isEmpty()){
      throw new ValidationException("NOT_FOUND_CASHIER_EMPLOYEE");
    }

//    Optional<JobPosition> jobPosition = JobPosition.find("title = ?1", "Cashier").firstResultOptional();
//    if(jobPosition.isEmpty()){
//      throw new ValidationException("NOT_FOUND_JOB_POSITION");
//    }

    for (OrderItemRequest item : orderItem){
      Optional<Item> itemId = Item.findByIdOptional(item.itemId);
      if(itemId.isEmpty()){
        throw new ValidationException("NOT_FOUND_ITEM_ORDER");
      }
    }

    Order order = orderPersist(employeeCashier.get(), paymentTypeId.get());
    Double subTotal = orderItemPersist(order, orderItem);
    orderFinalPersist(order.getId(), subTotal);
    return Response.ok(new HashMap<>()).build();
  }

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public Order orderPersist(Employee employeeId, PaymentType paymentTypeId){
    Order order = new Order();
    order.setCashier(employeeId);
    order.setPaymentTypeId(paymentTypeId);
    order.setTotal(0.0);
    order.setTax(0.0);
    order.setSubTotal(0.0);
    order.setCode("randomCode");
    order.persist();
    return order;
  }

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public Double orderItemPersist(Order order, List<OrderItemRequest> orderItemList){
    List<OrderItem> orderItems = new ArrayList<>();
    Double subTotal = 0.0;

    for (OrderItemRequest orderItem : orderItemList){
      Optional<Item> item = Item.findByIdOptional(orderItem.itemId);
      if (item.isEmpty()){
        throw new ValidationException("NOT_FOUND_ITEM_ID");
      }
      OrderItem orderItemModel = new OrderItem();
      orderItemModel.setOrderId(order);
      orderItemModel.setItemId(item.get());
      orderItemModel.setQuantity(orderItem.quantity);
      orderItemModel.setNote(orderItem.note);
      orderItemModel.setPriceTotal(item.get().getPrice() * orderItem.quantity);
      orderItems.add(orderItemModel);
      subTotal += orderItemModel.getPriceTotal();
      orderItemModel.persist();
      if (!orderItemModel.isPersistent()){
        throw new ValidationException("INVALID_CREATE_ITEM");
      }
    }

    return subTotal;
  }

  @Transactional
  public Order orderFinalPersist(String orderId, Double subTotal){
    Order order = Order.findById(orderId);
    order.setTotal(subTotal);
    order.setTax(subTotal * 0.11);
    order.setTotal(subTotal + order.getTax());
    order.persist();
    return order;
  }

  public Response get(String userId, Integer page, String month){
    Optional<User>  userOptional = User.findByIdOptional(userId);
    if(userOptional.isEmpty()){
      throw new ValidationException("USER_NOT_FOUND_INVALID_REQUEST");
    }

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));

    List<Order> orders =
        Order.find("TO_CHAR(created_at, 'MON') = ?1", month.toUpperCase(Locale.ROOT))
            .page(page-1, 10).list();

    List<Map<String,Object>> result = objectMapper.convertValue(orders, List.class);
    ListIterator<Map<String,Object>> listIterator = result.listIterator();

    while (listIterator.hasNext()){
      Map<String,Object> jsonObject = listIterator.next();
      String orderId = jsonObject.get("id").toString();
      List<OrderItem> orderItems = OrderItem.list("order_id = ?1", orderId);

      jsonObject.put("orderItem", orderItems);
      listIterator.set(jsonObject);
    }

    return Response.ok(result).build();
  }
}
