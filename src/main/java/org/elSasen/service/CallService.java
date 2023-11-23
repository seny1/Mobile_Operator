package org.elSasen.service;

import org.elSasen.dao.CallDao;
import org.elSasen.dto.insert.CallDtoInsert;
import org.elSasen.dto.select.CallDto;
import org.elSasen.entities.Call;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.CallMapper;
import org.elSasen.validator.CallValidator;
import org.elSasen.validator.ValidationResult;

import java.util.List;
import java.util.stream.Collectors;

public class CallService {

    private static final CallService INSTANCE = new CallService();

    private final CallDao callDao = CallDao.getInstance();
    private final CallValidator callValidator = CallValidator.getInstance();

    private final CallMapper callMapper = CallMapper.getInstance();
    public List<CallDto> getCallTable(String orderBy) {
        var callTable = callDao.getCallTable(orderBy);
        return callTable.stream()
                .map(callMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoCallTable(CallDtoInsert callDtoInsert) {
        var validationResult = callValidator.isValid(callDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var call = callMapper.mapFrom(callDtoInsert);
        callDao.insertIntoCallTable(call);
    }

    public List<String> getColumnsOfCall() {
        return callDao.getMetaData();
    }
    public List<String> getGoodColumnsOfCall() {
        return callDao.getGoodMetaData();
    }

    public static CallService getInstance() {
        return INSTANCE;
    }
}
