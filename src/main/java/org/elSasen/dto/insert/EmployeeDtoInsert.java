package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class EmployeeDtoInsert {
    String departmentName;
    String salonAddress;
    String firstName;
    String lastName;
    String postName;
    String series;
    String numberOfPassport;
    LocalDate birthday;
    LocalDate issueDate;
    String placeCode;
    String workNumber;
    String personalNumber;
    String login;
    String password;
    String roleName;
}
