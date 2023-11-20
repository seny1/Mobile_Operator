package org.elSasen.service;

import org.elSasen.dao.EmployeeDao;
import org.elSasen.dto.EmployeeDto;
import org.elSasen.mapper.EmployeeMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeService {

    private static final EmployeeService INSTANCE = new EmployeeService();
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();
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

    public void insertIntoEmployee(String departmentName, String salonAddress, String firstName, String lastName, String postName, String series, String number, LocalDate birthday, LocalDate issueDate, String placeCode, String workNumber, String personalNumber, String login, String password, String roleName) {
        employeeDao.insertIntoEmployee(departmentName, salonAddress, firstName, lastName, postName, series, number, birthday, issueDate, placeCode, workNumber, personalNumber, login, password, roleName);
    }

    public List<String> getColumnsOfEmployee() {
        return employeeDao.getMetaData();
    }

    public static EmployeeService getInstance() {
        return INSTANCE;
    }
}
