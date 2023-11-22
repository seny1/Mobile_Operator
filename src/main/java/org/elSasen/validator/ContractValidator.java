package org.elSasen.validator;

import org.elSasen.dto.insert.ContractDtoInsert;
import org.elSasen.entities.TariffPlan;
import org.elSasen.service.ClientService;
import org.elSasen.service.TariffPlanService;

import java.time.LocalDate;
import java.util.List;

public class ContractValidator implements Validator<ContractDtoInsert> {

    private static final ContractValidator INSTANCE = new ContractValidator();
    @Override
    public ValidationResult isValid(ContractDtoInsert contractDtoInsert) {
        var validationResult = new ValidationResult();
        var tariffPlanService = TariffPlanService.getInstance();
        var clientService = ClientService.getInstance();

        var plans = tariffPlanService.getPlans();
        boolean planNameFlag = false;
        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i).equals(contractDtoInsert.getPlanName())) {
                planNameFlag = true;
                break;
            }
        }
        if (!planNameFlag) {
            validationResult.add(Error.of("invalid.planName", "Такого плана нет"));
        }

        if (clientService.getClientById(contractDtoInsert.getClientId()).isEmpty()) {
            validationResult.add(Error.of("invalid.clientId", "Клиента с таким ID не существует"));
        }

        if (contractDtoInsert.getDate().isAfter(LocalDate.now())) {
            validationResult.add(Error.of("invalid.date", "Дата не может быть больше сегодняшней"));
        }

        return validationResult;
    }

    public static ContractValidator getInstance() {
        return INSTANCE;
    }
}
