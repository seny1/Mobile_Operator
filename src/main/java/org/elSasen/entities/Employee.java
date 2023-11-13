package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
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
