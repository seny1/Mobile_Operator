package org.elSasen.service;

import org.elSasen.dao.OrderDao;
import org.elSasen.dto.OrderDto;
import org.elSasen.mapper.OrderMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderService {
    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final OrderMapper orderMapper = OrderMapper.getInstance();

    public List<OrderDto> getOrderTable(String orderBy) {
        var orderTable = orderDao.getOrderTable(orderBy);
        return orderTable.stream()
                .map(orderMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfOrder() {
        return orderDao.getMetaData();
    }

    public void insertIntoOrder(String serviceName, int employeeId, int clientId, String model, String clientProblem, String comment) {
        orderDao.insertIntoOrder(serviceName, employeeId, clientId, model, clientProblem, comment);
    }
    public static OrderService getInstance() {
        return INSTANCE;
    }
}
