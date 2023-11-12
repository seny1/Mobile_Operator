package org.elSasen.entities;

import lombok.Data;

@Data
public class Employee {
    long employeeId;
    long departmentId;
    long salonId;
    String firstName;
    String lastName;
    long postId;
    long passportId;
    int contactId;
}
