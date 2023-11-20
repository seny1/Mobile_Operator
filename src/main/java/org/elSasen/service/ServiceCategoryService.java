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

    public List<ServiceCategoryDto> getServiceCategoryTable(String orderBy) {
        var serviceCategoryTable = serviceCategoryDao.getServiceCategoryTable(orderBy);
        return serviceCategoryTable.stream()
                .map(serviceCategoryMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfServiceCategory() {
        return serviceCategoryDao.getMetaData();
    }

    public List<String> getServiceCategories() {
        return serviceCategoryDao.getServiceCategories();
    }
    public static ServiceCategoryService getInstance() {
        return INSTANCE;
    }
}
