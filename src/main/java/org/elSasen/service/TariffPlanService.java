package org.elSasen.service;

import org.elSasen.dao.TariffPlanDao;
import org.elSasen.dto.insert.TariffPlanDtoInsert;
import org.elSasen.dto.select.CheckDto;
import org.elSasen.dto.select.TariffPlanDto;
import org.elSasen.entities.Check;
import org.elSasen.entities.TariffPlan;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.TariffPlanMapper;
import org.elSasen.validator.TariffPlanValidator;
import org.elSasen.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<TariffPlanDto> getFilterTariffTable(String orderBy, Map<String, String> filterMap) {
        var tariffPlanTable = tariffPlanDao.getTariffPlanTable(orderBy);
        var result = new ArrayList<TariffPlan>();
        result = (ArrayList<TariffPlan>) tariffPlanTable;
        for (int i = 0; i < tariffPlanTable.size();) {
            var tariff = tariffPlanTable.get(i);
            if (!filterMap.get("planName").isEmpty() && !tariff.getPlanName().equals(filterMap.get("planName"))) {
                result.remove(i);
            } else if (!filterMap.get("callMinutesUp").isEmpty() && !(tariff.getCallMinutes() > Integer.parseInt(filterMap.get("callMinutesUp")))) {
                result.remove(i);
            } else if (!filterMap.get("callMinutesDown").isEmpty() && !(tariff.getCallMinutes() < Integer.parseInt(filterMap.get("callMinutesDown")))) {
                result.remove(i);
            } else if (!filterMap.get("internetGbUp").isEmpty() && !(tariff.getInternetGb() > Integer.parseInt(filterMap.get("internetGbUp")))) {
                result.remove(i);
            } else if (!filterMap.get("internetGbDown").isEmpty() && !(tariff.getInternetGb() < Integer.parseInt(filterMap.get("internetGbDown")))) {
                result.remove(i);
            } else if (!filterMap.get("smsNumberUp").isEmpty() && !(tariff.getSmsNumber() > Integer.parseInt(filterMap.get("smsNumberUp")))) {
                result.remove(i);
            } else if (!filterMap.get("smsNumberDown").isEmpty() && !(tariff.getSmsNumber() < Integer.parseInt(filterMap.get("smsNumberDown")))) {
                result.remove(i);
            } else if (!filterMap.get("priceUp").isEmpty() && !(tariff.getPrice() > Integer.parseInt(filterMap.get("priceUp")))) {
                result.remove(i);
            } else if (!filterMap.get("priceDown").isEmpty() && !(tariff.getPrice() < Integer.parseInt(filterMap.get("priceDown")))) {
                result.remove(i);
            }
            else {
                i++;
            }
        }
        return result.stream()
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
