package org.elSasen.service;

import org.elSasen.dao.ProductDao;
import org.elSasen.dto.ProductDto;
import org.elSasen.mapper.ProductMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductMapper productMapper = ProductMapper.getInstance();

    public Set<ProductDto> getProductTable() {
        var productTable = productDao.getProductTable();
        return productTable.stream()
                .map(productMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfProduct() {
        return productDao.getMetaData();
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }
}
