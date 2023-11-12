package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeDto {
    long employeeId;
    long departmentId;
    long salonId;
    String firstName;
    String lastName;
    long postId;
    long passportId;
    int contactId;
    String login;
    String password;
    long roleId;
}
