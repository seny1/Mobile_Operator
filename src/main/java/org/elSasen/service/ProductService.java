package org.elSasen.service;

import org.elSasen.dao.ProductDao;
import org.elSasen.dto.insert.ProductDtoInsert;
import org.elSasen.dto.select.ProductDto;
import org.elSasen.dto.select.TariffPlanDto;
import org.elSasen.entities.Product;
import org.elSasen.entities.TariffPlan;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ProductMapper;
import org.elSasen.validator.ProductValidator;
import org.elSasen.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<String> getGoodColumnsOfProduct() {
        return productDao.getGoodMetaData();
    }
    public List<ProductDto> getFilterProductTable(String orderBy, Map<String, String> filterMap) {
        var productTable = productDao.getProductTable(orderBy);
        var result = new ArrayList<Product>();
        result = (ArrayList<Product>) productTable;
        for (int i = 0; i < productTable.size();) {
            var product = productTable.get(i);
            if (!filterMap.get("productName").isEmpty() && !product.getProductName().equals(filterMap.get("productName"))) {
                result.remove(i);
            } else if (!filterMap.get("priceUp").isEmpty() && !(product.getPrice() > Integer.parseInt(filterMap.get("priceUp")))) {
                result.remove(i);
            } else if (!filterMap.get("priceDown").isEmpty() && !(product.getPrice() < Integer.parseInt(filterMap.get("priceDown")))) {
                result.remove(i);
            } else if (!filterMap.get("countUp").isEmpty() && !(product.getCount() > Integer.parseInt(filterMap.get("countUp")))) {
                result.remove(i);
            } else if (!filterMap.get("countDown").isEmpty() && !(product.getCount() < Integer.parseInt(filterMap.get("countDown")))) {
                result.remove(i);
            }
            else {
                i++;
            }
        }
        return result.stream()
            .map(productMapper::mapFrom)
            .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductByName(String name) {
        return productDao.getProductByName(name)
                .map(productMapper::mapFrom);
    }
    public static ProductService getInstance() {
        return INSTANCE;
    }
}
