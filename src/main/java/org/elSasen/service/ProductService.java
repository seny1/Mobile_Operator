package org.elSasen.service;

import org.elSasen.dao.ProductDao;
import org.elSasen.dto.insert.ProductDtoInsert;
import org.elSasen.dto.select.ProductDto;
import org.elSasen.entities.Product;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ProductMapper;
import org.elSasen.validator.ProductValidator;
import org.elSasen.validator.ValidationResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductMapper productMapper = ProductMapper.getInstance();
    private final ProductValidator productValidator = ProductValidator.getInstance();

    public List<ProductDto> getProductTable(String orderBy) {
        var productTable = productDao.getProductTable(orderBy);
        return productTable.stream()
                .map(productMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoProductTable(ProductDtoInsert productDtoInsert) {
        var validationResult = productValidator.isValid(productDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var product = productMapper.mapFrom(productDtoInsert);
        productDao.insertIntoProductTable(product);
    }
    public List<String> getProductNames() {
        return productDao.getProductNames();
    }

    public List<String> getColumnsOfProduct() {
        return productDao.getMetaData();
    }

    public Optional<ProductDto> getProductByName(String name) {
        return productDao.getProductByName(name)
                .map(productMapper::mapFrom);
    }
    public static ProductService getInstance() {
        return INSTANCE;
    }
}
