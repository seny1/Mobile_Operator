package org.elSasen.service;

import org.elSasen.dao.ExtraServiceDao;
import org.elSasen.dto.ExtraServiceDto;
import org.elSasen.mapper.ExtraServiceMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExtraServiceService {
    private static final ExtraServiceService INSTANCE = new ExtraServiceService();
    private final ExtraServiceDao extraServiceDao = ExtraServiceDao.getInstance();
    private final ExtraServiceMapper extraServiceMapper = ExtraServiceMapper.getInstance();

    public List<ExtraServiceDto> getExtraServiceTable(String orderBy) {
        var extraServiceTable = extraServiceDao.getExtraServiceTable(orderBy);
        return extraServiceTable.stream()
                .map(extraServiceMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfExtraService() {
        return extraServiceDao.getMetaData();
    }

    public List<String> getServices() {
        return extraServiceDao.getServices();
    }
    public static ExtraServiceService getInstance() {
        return INSTANCE;
    }
}
