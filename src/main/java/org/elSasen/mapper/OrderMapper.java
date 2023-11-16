package org.elSasen.mapper;

import org.elSasen.dao.OrderDao;
import org.elSasen.dto.ExtraServiceDto;
import org.elSasen.dto.OrderDto;
import org.elSasen.entities.ExtraService;
import org.elSasen.entities.Order;

public class OrderMapper implements Mapper<Order, OrderDto> {

    @Override
    public OrderDto mapFrom(Order order) {
        return OrderDto.builder()
                .service(order.getService())
                .employee(order.getEmployee())
                .client(order.getClient())
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .device(order.getDevice())
                .comment(order.getComment())
                .build();
    }
    private static final OrderMapper INSTANCE = new OrderMapper();
    public static OrderMapper getInstance() {
        return INSTANCE;
    }
}
