package priv.just.framework.security.service;

import priv.just.framework.security.domain.Order;

public interface OrderService {

    Order queryOrder(long orderId);

    long createOrder(Order order);

    void deleteOrder(long orderId);

}
