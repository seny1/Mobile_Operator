package org.elSasen.service;

import org.elSasen.dao.TariffPlanDao;
import org.elSasen.dto.TariffPlanDto;
import org.elSasen.mapper.TariffPlanMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TariffPlanService {
    private static final TariffPlanService INSTANCE = new TariffPlanService();
    private final TariffPlanDao tariffPlanDao = TariffPlanDao.getInstance();
    private final TariffPlanMapper tariffPlanMapper = TariffPlanMapper.getInstance();

    public Set<TariffPlanDto> getTariffPlanTable() {
        var tariffPlanTable = tariffPlanDao.getTariffPlanTable();
        return tariffPlanTable.stream()
                .map(tariffPlanMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfTariffPlan() {
        return tariffPlanDao.getMetaData();
    }

    public static TariffPlanService getInstance() {
        return INSTANCE;
    }
}
