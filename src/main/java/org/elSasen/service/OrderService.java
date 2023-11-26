package org.elSasen.service;

import org.elSasen.dao.OrderDao;
import org.elSasen.dto.insert.OrderDtoInsert;
import org.elSasen.dto.select.ExtraServiceDto;
import org.elSasen.dto.select.OrderDto;
import org.elSasen.entities.ExtraService;
import org.elSasen.entities.Order;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.OrderMapper;
import org.elSasen.validator.OrderValidator;
import org.elSasen.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<OrderDto> getFilterOrderTable(String orderBy, Map<String, String> filterMap) {
        var orderTable = orderDao.getOrderTable(orderBy);
        var result = new ArrayList<Order>();
        result = (ArrayList<Order>) orderTable;
        for (int i = 0; i < orderTable.size();) {
            var order = orderTable.get(i);
            if (!filterMap.get("serviceName").isEmpty() && !order.getService().getServiceName().equals(filterMap.get("serviceName"))) {
                result.remove(i);
            } else if (!filterMap.get("employeeFirstName").isEmpty() && !order.getEmployee().getFirstName().equals(filterMap.get("employeeFirstName"))) {
                result.remove(i);
            } else if (!filterMap.get("employeeLastName").isEmpty() && !order.getEmployee().getLastName().equals(filterMap.get("employeeLastName"))) {
                result.remove(i);
            } else if (!filterMap.get("clientFirstName").isEmpty() && !order.getClient().getFirstName().equals(filterMap.get("clientFirstName"))) {
                result.remove(i);
            } else if (!filterMap.get("clientLastName").isEmpty() && !order.getClient().getLastName().equals(filterMap.get("clientLastName"))) {
                result.remove(i);
            } else if (!filterMap.get("model").isEmpty() && !order.getDevice().getModel().equals(filterMap.get("model"))) {
                result.remove(i);
            }
            else {
                i++;
            }
        }
        return result.stream()
            .map(orderMapper::mapFrom)
            .collect(Collectors.toList());
    }

    public List<String> getColumnsOfOrder() {
        return orderDao.getMetaData();
    }

    public List<String> getGoodColumnsOfOrder() {
        return orderDao.getGoodMetaData();
    }
    public void insertIntoOrder(OrderDtoInsert orderDtoInsert) {
        var validationResult = orderValidator.isValid(orderDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var order = orderMapper.mapFrom(orderDtoInsert);
        orderDao.insertIntoOrder(order);
    }

    public void updateStatus(int orderId, String statusName) {
        orderDao.updateStatus(orderId, statusName);
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
