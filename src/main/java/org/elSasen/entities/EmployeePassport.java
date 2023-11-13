package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmployeePassport {
    long passportId;
    String series;
    String number;
    LocalDate birthday;
    LocalDate issueDate;
    String placeCode;
}
