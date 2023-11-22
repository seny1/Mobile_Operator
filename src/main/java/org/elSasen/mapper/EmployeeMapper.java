package org.elSasen.mapper;

import org.elSasen.dto.insert.EmployeeDtoInsert;
import org.elSasen.dto.select.EmployeeDto;
import org.elSasen.entities.*;

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

    public Employee mapFrom(EmployeeDtoInsert employeeDtoInsert) {
        return Employee.builder()
                .department(Department.builder()
                        .departmentName(employeeDtoInsert.getDepartmentName())
                        .build())
                .salon(CommunicationSalon.builder()
                        .address(employeeDtoInsert.getSalonAddress())
                        .build())
                .firstName(employeeDtoInsert.getFirstName())
                .lastName(employeeDtoInsert.getLastName())
                .post(Post.builder()
                        .postName(employeeDtoInsert.getPostName())
                        .build())
                .passport(EmployeePassport.builder()
                        .series(employeeDtoInsert.getSeries())
                        .number(employeeDtoInsert.getNumberOfPassport())
                        .birthday(employeeDtoInsert.getBirthday())
                        .issueDate(employeeDtoInsert.getIssueDate())
                        .placeCode(employeeDtoInsert.getPlaceCode())
                        .build())
                .contact(EmployeeContact.builder()
                        .workNumber(employeeDtoInsert.getWorkNumber())
                        .personalNumber(employeeDtoInsert.getPersonalNumber())
                        .build())
                .login(employeeDtoInsert.getLogin())
                .password(employeeDtoInsert.getPassword())
                .role(Role.builder()
                        .roleName(employeeDtoInsert.getRoleName())
                        .build())
                .build();
    }

    public static EmployeeMapper getInstance() {
        return INSTANCE;
    }
}
