package org.elSasen.mapper;

import org.elSasen.dto.insert.OrderDtoInsert;
import org.elSasen.dto.select.OrderDto;
import org.elSasen.entities.*;

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

    public Order mapFrom(OrderDtoInsert orderDtoInsert) {
        return Order.builder()
                .service(ExtraService.builder()
                        .serviceName(orderDtoInsert.getServiceName())
                        .build())
                .employee(Employee.builder()
                        .employeeId(orderDtoInsert.getEmployeeId())
                        .build())
                .client(Client.builder()
                        .clientId(orderDtoInsert.getClientId())
                        .build())
                .device(ClientDevice.builder()
                        .model(orderDtoInsert.getModel())
                        .clientProblem(orderDtoInsert.getClientProblem())
                        .build())
                .comment(orderDtoInsert.getComment())
                .build();
    }
    private static final OrderMapper INSTANCE = new OrderMapper();
    public static OrderMapper getInstance() {
        return INSTANCE;
    }
}
