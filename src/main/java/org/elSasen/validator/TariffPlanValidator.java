package org.elSasen.validator;

import org.elSasen.dto.insert.TariffPlanDtoInsert;

public class TariffPlanValidator implements Validator<TariffPlanDtoInsert> {

    private static final TariffPlanValidator INSTANCE = new TariffPlanValidator();
    @Override
    public ValidationResult isValid(TariffPlanDtoInsert tariffPlanDtoInsert) {
        var validationResult = new ValidationResult();

        if (tariffPlanDtoInsert.getPlanName().length() > 32) {
            validationResult.add(Error.of("invalid.tariffName", "Слишком длинное имя"));
        }

        return validationResult;
    }

    public static TariffPlanValidator getInstance() {
        return INSTANCE;
    }
}
