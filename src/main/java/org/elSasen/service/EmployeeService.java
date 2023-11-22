package org.elSasen.service;

import org.elSasen.dao.EmployeeDao;
import org.elSasen.dto.insert.EmployeeDtoInsert;
import org.elSasen.dto.select.EmployeeDto;
import org.elSasen.entities.Employee;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.EmployeeMapper;
import org.elSasen.validator.EmployeeValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeService {

    private static final EmployeeService INSTANCE = new EmployeeService();
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();
    private final EmployeeValidator employeeValidator = EmployeeValidator.getInstance();
    private final EmployeeMapper employeeMapper = EmployeeMapper.getInstance();
    public Optional<EmployeeDto> findUser(String login, String password) {
        return employeeDao.findByLoginAndPassword(login, password)
                .map(employeeMapper::mapFrom);
    }

    public List<EmployeeDto> getEmployeeTable(String orderBy) {
        return employeeDao.getEmployeeTable(orderBy).stream()
                .map(employeeMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoEmployee(EmployeeDtoInsert employeeDtoInsert) {
        var validationResult = employeeValidator.isValid(employeeDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var employee = employeeMapper.mapFrom(employeeDtoInsert);
        employeeDao.insertIntoEmployee(employee);
    }

    public List<String> getColumnsOfEmployee() {
        return employeeDao.getMetaData();
    }

    public static EmployeeService getInstance() {
        return INSTANCE;
    }
}
