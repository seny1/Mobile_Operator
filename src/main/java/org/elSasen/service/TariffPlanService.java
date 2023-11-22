package org.elSasen.service;

import org.elSasen.dao.TariffPlanDao;
import org.elSasen.dto.select.TariffPlanDto;
import org.elSasen.mapper.TariffPlanMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TariffPlanService {
    private static final TariffPlanService INSTANCE = new TariffPlanService();
    private final TariffPlanDao tariffPlanDao = TariffPlanDao.getInstance();
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

    public void insertIntoTariffPlan(String planName, int callMinutes, int internetGb, int smsNumber, int price) {
        tariffPlanDao.insertIntoTariffPlan(planName, callMinutes, internetGb, smsNumber, price);
    }
    public static TariffPlanService getInstance() {
        return INSTANCE;
    }
}
