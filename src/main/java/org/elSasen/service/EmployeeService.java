package org.elSasen.service;

import org.elSasen.dao.EmployeeDao;
import org.elSasen.dto.insert.EmployeeDtoInsert;
import org.elSasen.dto.select.EmployeeDto;
import org.elSasen.entities.Call;
import org.elSasen.entities.Employee;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.EmployeeMapper;
import org.elSasen.validator.EmployeeValidator;
import org.elSasen.validator.Error;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<EmployeeDto> getFilterEmployeeTable(String orderBy, HashMap<String, String> filterMap) {
        var employeeTable = employeeDao.getEmployeeTable(orderBy);
        var result = new ArrayList<Employee>();
        result = (ArrayList<Employee>) employeeTable;
        for (int i = 0; i < employeeTable.size(); ) {
            var employee = employeeTable.get(i);
            if (!filterMap.get("firstName").isEmpty() && !employee.getFirstName().startsWith(filterMap.get("firstName"))) {
                result.remove(i);
            } else if (!filterMap.get("lastName").isEmpty() && !(employee.getLastName().startsWith(filterMap.get("lastName")))) {
                result.remove(i);
            } else if (!filterMap.get("department").isEmpty() && !employee.getDepartment().getDepartmentName().equals(filterMap.get("department"))) {
                result.remove(i);
            } else if (!filterMap.get("address").isEmpty() && !employee.getSalon().getAddress().equals(filterMap.get("address"))) {
                result.remove(i);
            } else if (!filterMap.get("post").isEmpty() && !(employee.getPost().getPostName().equals(filterMap.get("post")))) {
                result.remove(i);
            } else if (!filterMap.get("series").isEmpty() && !(employee.getPassport().getSeries().startsWith(filterMap.get("series")))) {
                result.remove(i);
            } else if (!filterMap.get("numberOfPassport").isEmpty() && !employee.getPassport().getNumber().startsWith(filterMap.get("numberOfPassport"))) {
                result.remove(i);
            } else if (!filterMap.get("birthdayUp").isEmpty() && employee.getPassport().getBirthday().isAfter(LocalDate.parse(filterMap.get("birthdayUp")))) {
                result.remove(i);
            } else if (!filterMap.get("birthdayDown").isEmpty() && employee.getPassport().getBirthday().isBefore(LocalDate.parse(filterMap.get("birthdayDown")))) {
                result.remove(i);
            } else {
                i++;
            }
        }
        return result.stream()
                .map(employeeMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void deleteClient(String login) {
        if (!employeeDao.deleteClient(login)) {
            throw new ValidationException(List.of(Error.of("invalid.login", "Сотрудника с таким логином не существует")));
        }
    }

    public List<String> getColumnsOfEmployee() {
        return employeeDao.getMetaData();
    }

    public List<String> getGoodColumnsOfEmployee() {
        return employeeDao.getGoodMetaData();
    }
    public static EmployeeService getInstance() {
        return INSTANCE;
    }
}
