package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;
import org.elSasen.entities.*;

@Value
@Builder
public class EmployeeDto {
    long employeeId;
    Department department;
    CommunicationSalon salon;
    String firstName;
    String lastName;
    Post post;
    EmployeePassport passport;
    EmployeeContact contact;
    String login;
    String password;
    Role role;
}
