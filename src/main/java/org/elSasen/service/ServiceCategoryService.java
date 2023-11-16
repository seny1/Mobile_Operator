package org.elSasen.service;

import org.elSasen.dao.RoleDao;
import org.elSasen.dao.ServiceCategoryDao;
import org.elSasen.dto.RoleDto;
import org.elSasen.dto.ServiceCategoryDto;
import org.elSasen.mapper.RoleMapper;
import org.elSasen.mapper.ServiceCategoryMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceCategoryService {
    private static final ServiceCategoryService INSTANCE = new ServiceCategoryService();
    private final ServiceCategoryDao serviceCategoryDao = ServiceCategoryDao.getInstance();
    private final ServiceCategoryMapper serviceCategoryMapper = ServiceCategoryMapper.getInstance();

    public Set<ServiceCategoryDto> getServiceCategoryTable() {
        var serviceCategoryTable = serviceCategoryDao.getServiceCategoryTable();
        return serviceCategoryTable.stream()
                .map(serviceCategoryMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfServiceCategory() {
        return serviceCategoryDao.getMetaData();
    }

    public static ServiceCategoryService getInstance() {
        return INSTANCE;
    }
}
