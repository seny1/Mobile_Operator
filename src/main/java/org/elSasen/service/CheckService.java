package org.elSasen.service;

import org.elSasen.dao.CheckDao;
import org.elSasen.dto.insert.CheckDtoInsert;
import org.elSasen.dto.select.CallDto;
import org.elSasen.dto.select.CheckDto;
import org.elSasen.entities.Call;
import org.elSasen.entities.Check;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.CheckMapper;
import org.elSasen.validator.CheckValidator;
import org.elSasen.validator.Error;
import org.elSasen.validator.ValidationResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<CheckDto> getFilterCallTable(String orderBy, Map<String, String> filterMap) {
        var checkTable = checkDao.getCheckTable(orderBy);
        var result = new ArrayList<Check>();
        result = (ArrayList<Check>) checkTable;
        for (int i = 0; i < checkTable.size();) {
            var check = checkTable.get(i);
            if (!filterMap.get("productName").isEmpty() && !check.getProduct().getProductName().equals(filterMap.get("productName"))) {
                result.remove(i);
            } else if (!filterMap.get("productCountUp").isEmpty() && !(check.getProductCount() > Integer.parseInt(filterMap.get("productCountUp")))) {
                result.remove(i);
            } else if (!filterMap.get("productCountDown").isEmpty() && !(check.getProductCount() < Integer.parseInt(filterMap.get("productCountDown")))) {
                result.remove(i);
            } else if (!filterMap.get("firstName").isEmpty() && !check.getClient().getFirstName().equals(filterMap.get("firstName"))) {
                result.remove(i);
            } else if (!filterMap.get("lastName").isEmpty() && !check.getClient().getLastName().equals(filterMap.get("lastName"))) {
                result.remove(i);
            } else {
                i++;
            }
        }
        return result.stream()
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

    public void deleteCheck(int checkId) {
        if (!checkDao.deleteCheck(checkId)) {
            throw new ValidationException(List.of(Error.of("invalid.check", "Чека с таким ID не существует")));
        }
    }

    public List<String> getGoodMetaData() {
        return checkDao.getGoodMetaData();
    }

    public List<String> getColumnsOfCheck() {
        return checkDao.getMetaData();
    }

    public static CheckService getInstance() {
        return INSTANCE;
    }
}
