package org.elSasen.service;

import org.elSasen.dao.CallDao;
import org.elSasen.dao.EmployeePassportDao;
import org.elSasen.dto.CallDto;
import org.elSasen.dto.EmployeePassportDto;
import org.elSasen.mapper.CallMapper;
import org.elSasen.mapper.EmployeePassportMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeePassportService {
    private static final EmployeePassportService INSTANCE = new EmployeePassportService();

    private final EmployeePassportDao employeePassportDao = EmployeePassportDao.getInstance();

    private final EmployeePassportMapper employeePassportMapper = EmployeePassportMapper.getInstance();
    public List<EmployeePassportDto> getEmployeePassportTable(String orderBy) {
        var employeePassportTable = employeePassportDao.getEmployeePassportTable(orderBy);
        return employeePassportTable.stream()
                .map(employeePassportMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfEmployeePassport() {
        return employeePassportDao.getMetaData();
    }

    public static EmployeePassportService getInstance() {
        return INSTANCE;
    }
}
