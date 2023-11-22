package org.elSasen.service;

import org.elSasen.dao.OrderDao;
import org.elSasen.dto.insert.OrderDtoInsert;
import org.elSasen.dto.select.OrderDto;
import org.elSasen.entities.Order;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.OrderMapper;
import org.elSasen.validator.OrderValidator;
import org.elSasen.validator.ValidationResult;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final OrderMapper orderMapper = OrderMapper.getInstance();
    private final OrderValidator orderValidator = OrderValidator.getInstance();

    public List<OrderDto> getOrderTable(String orderBy) {
        var orderTable = orderDao.getOrderTable(orderBy);
        return orderTable.stream()
                .map(orderMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfOrder() {
        return orderDao.getMetaData();
    }

    public void insertIntoOrder(OrderDtoInsert orderDtoInsert) {
        var validationResult = orderValidator.isValid(orderDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var order = orderMapper.mapFrom(orderDtoInsert);
        orderDao.insertIntoOrder(order);
    }
    public static OrderService getInstance() {
        return INSTANCE;
    }
}
