package priv.just.framework.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import priv.just.framework.security.domain.Order;
import priv.just.framework.security.service.OrderService;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("getTimestamp")
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    @PreAuthorize("hasAuthority('QUERY_ORDER')")
    @GetMapping("queryOrder")
    public Order queryOrder(long orderId) {
        return orderService.queryOrder(orderId);
    }

    @PreAuthorize("hasAuthority('CREATE_ORDER')")
    @PostMapping("createOrder")
    public long createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PreAuthorize("hasAuthority('DELETE_ORDER')")
    @PostMapping("deleteOrder")
    public void deleteOrder(@RequestParam("orderId") long orderId) {
        orderService.deleteOrder(orderId);
    }

}
