package org.elSasen.service;

import org.elSasen.dao.CheckDao;
import org.elSasen.dto.insert.CheckDtoInsert;
import org.elSasen.dto.select.CheckDto;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.CheckMapper;
import org.elSasen.validator.CheckValidator;
import org.elSasen.validator.ValidationResult;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CheckService {

    private static final CheckService INSTANCE = new CheckService();
    private final CheckDao checkDao = CheckDao.getInstance();
    private final CheckMapper checkMapper = CheckMapper.getInstance();
    private final CheckValidator checkValidator = CheckValidator.getInstance();

    public List<CheckDto> getCheckTable(String orderBy) {
        var checkTable = checkDao.getCheckTable(orderBy);
        return checkTable.stream()
                .map(checkMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoCheckTable(CheckDtoInsert checkDtoInsert) {
        var validationResult = checkValidator.isValid(checkDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var productNamesCount = new HashMap<String, Integer>();
        for (int i = 0; i < checkDtoInsert.getProductName().length; i++) {
            productNamesCount.put(checkDtoInsert.getProductName()[i], checkDtoInsert.getProductCount()[i]);
        }
        checkDao.insertIntoCheckTable(productNamesCount, checkDtoInsert.getClientId());
    }

    public List<String> getColumnsOfCheck() {
        return checkDao.getMetaData();
    }

    public static CheckService getInstance() {
        return INSTANCE;
    }
}
