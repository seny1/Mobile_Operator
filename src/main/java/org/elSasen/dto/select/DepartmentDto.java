package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;

import java.time.LocalTime;

@Value
@Builder
public class DepartmentDto {

    long departmentId;
    String departmentName;
    LocalTime startTime;
    LocalTime endTime;
}
