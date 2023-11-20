package org.elSasen.service;

import org.elSasen.dao.ProductCategoryDao;
import org.elSasen.dto.ProductCategoryDto;
import org.elSasen.mapper.ProductCategoryMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductCategoryService {
    private static final ProductCategoryService INSTANCE = new ProductCategoryService();
    private final ProductCategoryDao productCategoryDao = ProductCategoryDao.getInstance();
    private final ProductCategoryMapper productCategoryMapper = ProductCategoryMapper.getInstance();

    public List<ProductCategoryDto> getProductCategoryTable(String orderBy) {
        var productCategoryTable = productCategoryDao.getProductCategoryTable(orderBy);
        return productCategoryTable.stream()
                .map(productCategoryMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfProductCategory() {
        return productCategoryDao.getMetaData();
    }

    public List<String> getCategories() {
        return productCategoryDao.getCategories();
    }
    public static ProductCategoryService getInstance() {
        return INSTANCE;
    }
}
