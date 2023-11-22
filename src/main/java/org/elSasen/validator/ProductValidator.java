package org.elSasen.validator;

import org.elSasen.dto.insert.ProductDtoInsert;
import org.elSasen.service.ProductCategoryService;

import java.util.List;

public class ProductValidator implements Validator<ProductDtoInsert> {

    private static final ProductValidator INSTANCE = new ProductValidator();
    @Override
    public ValidationResult isValid(ProductDtoInsert productDtoInsert) {
        var validationResult = new ValidationResult();

        if (productDtoInsert.getProductName().length() > 32) {
            validationResult.add(Error.of("invalid.length", "Слишком длинное имя продукта"));
        }

        var productCategoryService = ProductCategoryService.getInstance();
        var categories = productCategoryService.getCategories();
        boolean categoryFlag = false;
        for (int i = 0; i < categories.size(); i++) {
            if (productDtoInsert.getCategoryName().equals(categories.get(i))) {
                categoryFlag = true;
                break;
            }
        }
        if (!categoryFlag) {
            validationResult.add(Error.of("invalid.category", "Такой категории нет"));
        }

        return validationResult;
    }

    public static ProductValidator getInstance() {
        return INSTANCE;
    }
}
