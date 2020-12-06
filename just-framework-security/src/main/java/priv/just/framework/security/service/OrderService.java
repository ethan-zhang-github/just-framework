package priv.just.framework.security.service;

import priv.just.framework.security.domain.Order;

import java.util.List;

public interface OrderService {

    Order queryOrder(long orderId);

    long createOrder(Order order);

    void deleteOrder(long orderId);

    List<Order> getAll();

}
