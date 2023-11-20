package org.elSasen.service;

import org.elSasen.dao.DepartmentDao;
import org.elSasen.dto.DepartmentDto;
import org.elSasen.mapper.DepartmentMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService {

    private static final DepartmentService INSTANCE = new DepartmentService();
    private final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private final DepartmentMapper departmentMapper = DepartmentMapper.getInstance();
    public List<DepartmentDto> getDepartmentTable(String orderBy) {
        var departmentTable = departmentDao.getDepartmentTable(orderBy);
        return departmentTable.stream()
                .map(departmentMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsNamesOfDepartment() {
        return departmentDao.getMetaData();
    }

    public List<String> getDepartments() {
        return departmentDao.getDepartments();
    }
    public static DepartmentService getInstance() {
        return INSTANCE;
    }
}
