package org.elSasen.service;

import org.elSasen.dao.EmployeeDao;
import org.elSasen.dto.EmployeeDto;
import org.elSasen.mapper.EmployeeMapper;

import java.util.Optional;

public class EmployeeService {

    private static final EmployeeService INSTANCE = new EmployeeService();
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();
    private final EmployeeMapper employeeMapper = EmployeeMapper.getInstance();
    public Optional<EmployeeDto> findUser(String login, String password) {
        return employeeDao.findByLoginAndPassword(login, password)
                .map(employeeMapper::mapFrom);
    }

    public static EmployeeService getInstance() {
        return INSTANCE;
    }
}
