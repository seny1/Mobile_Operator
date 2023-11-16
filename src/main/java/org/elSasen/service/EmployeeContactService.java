package org.elSasen.service;


import org.elSasen.dao.EmployeeContactDao;
import org.elSasen.dto.EmployeeContactDto;
import org.elSasen.mapper.EmployeeContactMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeContactService {

    private static final EmployeeContactService INSTANCE = new EmployeeContactService();
    private final EmployeeContactDao employeeContactDao = EmployeeContactDao.getInstance();
    private final EmployeeContactMapper employeeContactMapper = EmployeeContactMapper.getInstance();

    public Set<EmployeeContactDto> getEmployeeContactTable() {
        return employeeContactDao.getEmployeeContactTable().stream()
                .map(employeeContactMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfEmployeeContact() {
        return employeeContactDao.getMetaData();
    }


    public static EmployeeContactService getInstance() {
        return INSTANCE;
    }
}
