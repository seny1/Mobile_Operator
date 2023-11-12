package org.elSasen.entities;

import lombok.Data;

import java.sql.Time;

@Data
public class Department {
    long departmentId;
    String departmentName;
    Time start_time;
    Time end_time;
}
