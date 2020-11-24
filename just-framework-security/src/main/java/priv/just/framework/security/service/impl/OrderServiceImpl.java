package priv.just.framework.security.service.impl;

import org.springframework.stereotype.Service;
import priv.just.framework.security.domain.Order;
import priv.just.framework.security.service.OrderService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class OrderServiceImpl implements OrderService {

    private final ConcurrentMap<Long, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order queryOrder(long orderId) {
        return orders.get(orderId);
    }

    @Override
    public long createOrder(Order order) {
        order.setOrderId(System.currentTimeMillis());
        orders.putIfAbsent(order.getOrderId(), order);
        return order.getOrderId();
    }

    @Override
    public void deleteOrder(long orderId) {
        orders.remove(orderId);
    }

}
