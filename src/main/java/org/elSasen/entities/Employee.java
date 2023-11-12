package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
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
