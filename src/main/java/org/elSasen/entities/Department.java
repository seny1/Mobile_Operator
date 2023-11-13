package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class Department {
    long departmentId;
    String departmentName;
    LocalTime startTime;
    LocalTime endTime;
}
