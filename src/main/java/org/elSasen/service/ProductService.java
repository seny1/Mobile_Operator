package org.elSasen.service;

import org.elSasen.dao.ProductDao;
import org.elSasen.dto.ProductDto;
import org.elSasen.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductMapper productMapper = ProductMapper.getInstance();

    public List<ProductDto> getProductTable(String orderBy) {
        var productTable = productDao.getProductTable(orderBy);
        return productTable.stream()
                .map(productMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoProductTable(double price, String productDescription, String productName, String categoryName, int count) {
        productDao.insertIntoProductTable(price, productDescription, productName, categoryName, count);
    }
    public List<String> getProductNames() {
        return productDao.getProductNames();
    }

    public List<String> getColumnsOfProduct() {
        return productDao.getMetaData();
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }
}
