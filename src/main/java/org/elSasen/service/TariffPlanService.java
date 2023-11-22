package org.elSasen.service;

import org.elSasen.dao.TariffPlanDao;
import org.elSasen.dto.insert.TariffPlanDtoInsert;
import org.elSasen.dto.select.TariffPlanDto;
import org.elSasen.entities.TariffPlan;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.TariffPlanMapper;
import org.elSasen.validator.TariffPlanValidator;
import org.elSasen.validator.ValidationResult;

import java.util.List;
import java.util.stream.Collectors;

public class TariffPlanService {
    private static final TariffPlanService INSTANCE = new TariffPlanService();
    private final TariffPlanDao tariffPlanDao = TariffPlanDao.getInstance();
    private final TariffPlanValidator tariffPlanValidator = TariffPlanValidator.getInstance();
    private final TariffPlanMapper tariffPlanMapper = TariffPlanMapper.getInstance();

    public List<TariffPlanDto> getTariffPlanTable(String orderBy) {
        var tariffPlanTable = tariffPlanDao.getTariffPlanTable(orderBy);
        return tariffPlanTable.stream()
                .map(tariffPlanMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getPlans() {
        return tariffPlanDao.getPlans();
    }

    public List<String> getColumnsOfTariffPlan() {
        return tariffPlanDao.getMetaData();
    }

    public void insertIntoTariffPlan(TariffPlanDtoInsert tariffPlanDtoInsert) {
        var validationResult = tariffPlanValidator.isValid(tariffPlanDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var tariffPlan = tariffPlanMapper.mapFrom(tariffPlanDtoInsert);
        tariffPlanDao.insertIntoTariffPlan(tariffPlan);
    }
    public static TariffPlanService getInstance() {
        return INSTANCE;
    }
}
