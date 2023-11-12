package org.elSasen.entities;

import lombok.Data;

import java.sql.Date;

@Data
public class EmployeePassport {
    long passportId;
    String series;
    String number;
    Date birthday;
    Date issue_date;
    String placeCode;
}
