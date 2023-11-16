package org.elSasen.mapper;

import org.elSasen.dto.DepartmentDto;
import org.elSasen.entities.Department;

public class DepartmentMapper implements Mapper<Department, DepartmentDto> {

    private static final DepartmentMapper INSTANCE = new DepartmentMapper();

    @Override
    public DepartmentDto mapFrom(Department department) {
        return DepartmentDto.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .startTime(department.getStartTime())
                .endTime(department.getEndTime())
                .build();
    }

    public static DepartmentMapper getInstance() {
        return INSTANCE;
    }
}
