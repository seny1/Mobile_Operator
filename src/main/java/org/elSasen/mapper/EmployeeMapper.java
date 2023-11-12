package org.elSasen.mapper;

import org.elSasen.dto.EmployeeDto;
import org.elSasen.entities.Employee;

public class EmployeeMapper implements Mapper<Employee, EmployeeDto>{

    private static final EmployeeMapper INSTANCE = new EmployeeMapper();
    @Override
    public EmployeeDto mapFrom(Employee employee) {
        return EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .departmentId(employee.getDepartmentId())
                .salonId(employee.getSalonId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .postId(employee.getPostId())
                .passportId(employee.getPassportId())
                .contactId(employee.getContactId())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .roleId(employee.getRoleId())
                .build();
    }

    public static EmployeeMapper getInstance() {
        return INSTANCE;
    }
}
