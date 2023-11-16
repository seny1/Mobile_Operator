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

    public Set<OrderDto> getOrderTable() {
        var orderTable = orderDao.getOrderTable();
        return orderTable.stream()
                .map(orderMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfOrder() {
        return orderDao.getMetaData();
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
