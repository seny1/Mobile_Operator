package org.elSasen.mapper;

import org.elSasen.dto.EmployeeDto;
import org.elSasen.entities.Employee;

public class EmployeeMapper implements Mapper<Employee, EmployeeDto>{

    private static final EmployeeMapper INSTANCE = new EmployeeMapper();
    @Override
    public EmployeeDto mapFrom(Employee employee) {
        return EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .department(employee.getDepartment())
                .salon(employee.getSalon())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .post(employee.getPost())
                .passport(employee.getPassport())
                .contact(employee.getContact())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .role(employee.getRole())
                .build();
    }

    public static EmployeeMapper getInstance() {
        return INSTANCE;
    }
}
