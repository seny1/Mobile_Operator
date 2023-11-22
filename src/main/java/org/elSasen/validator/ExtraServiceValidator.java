package org.elSasen.validator;

import org.elSasen.dto.insert.ExtraServiceDtoInsert;
import org.elSasen.service.ServiceCategoryService;

import java.util.List;

public class ExtraServiceValidator implements Validator<ExtraServiceDtoInsert> {

    private static final ExtraServiceValidator INSTANCE = new ExtraServiceValidator();
    @Override
    public ValidationResult isValid(ExtraServiceDtoInsert extraServiceDtoInsert) {
        var validationResult = new ValidationResult();
        var serviceCategoryService = ServiceCategoryService.getInstance();

        if (extraServiceDtoInsert.getServiceName().length() > 32) {
            validationResult.add(Error.of("invalid.length", "Название услуги слишком длинное"));
        }

        var serviceCategories = serviceCategoryService.getServiceCategories();
        boolean categoryFlag = false;
        for (int i = 0; i < serviceCategories.size(); i++) {
            if (serviceCategories.get(i).equals(extraServiceDtoInsert.getCategoryName())) {
                categoryFlag = true;
                break;
            }
        }
        if (!categoryFlag) {
            validationResult.add(Error.of("invalid.category", "Такой категории нет"));
        }

        return validationResult;
    }

    public static ExtraServiceValidator getInstance() {
        return INSTANCE;
    }
}
