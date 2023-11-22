package org.elSasen.service;


import org.elSasen.dao.EmployeeContactDao;
import org.elSasen.dto.select.EmployeeContactDto;
import org.elSasen.mapper.EmployeeContactMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeContactService {

    private static final EmployeeContactService INSTANCE = new EmployeeContactService();
    private final EmployeeContactDao employeeContactDao = EmployeeContactDao.getInstance();
    private final EmployeeContactMapper employeeContactMapper = EmployeeContactMapper.getInstance();

    public List<EmployeeContactDto> getEmployeeContactTable(String orderBy) {
        return employeeContactDao.getEmployeeContactTable(orderBy).stream()
                .map(employeeContactMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfEmployeeContact() {
        return employeeContactDao.getMetaData();
    }


    public static EmployeeContactService getInstance() {
        return INSTANCE;
    }
}
