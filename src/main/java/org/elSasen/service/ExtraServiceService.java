package org.elSasen.service;

import org.elSasen.dao.ExtraServiceDao;
import org.elSasen.dto.insert.ExtraServiceDtoInsert;
import org.elSasen.dto.select.ExtraServiceDto;
import org.elSasen.entities.ExtraService;
import org.elSasen.entities.ServiceCategory;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ExtraServiceMapper;
import org.elSasen.validator.ExtraServiceValidator;

import java.util.List;
import java.util.stream.Collectors;

public class ExtraServiceService {
    private static final ExtraServiceService INSTANCE = new ExtraServiceService();
    private final ExtraServiceDao extraServiceDao = ExtraServiceDao.getInstance();
    private final ExtraServiceValidator extraServiceValidator = ExtraServiceValidator.getInstance();
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
    public List<String> getGoodColumnsOfExtraService() {
        return extraServiceDao.getGoodMetaData();
    }

    public List<String> getServices() {
        return extraServiceDao.getServices();
    }

    public void insertIntoService(ExtraServiceDtoInsert extraServiceDtoInsert) {
        var validationResult = extraServiceValidator.isValid(extraServiceDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var extraService = extraServiceMapper.mapFrom(extraServiceDtoInsert);
        extraServiceDao.insertIntoService(extraService);
    }

    public static ExtraServiceService getInstance() {
        return INSTANCE;
    }
}
