package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;
@Value
@Builder
    public class EmployeePassportDto {
        long passportId;
        String series;
        String number;
        LocalDate birthday;
        LocalDate issueDate;
        String placeCode;
    }