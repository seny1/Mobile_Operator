package org.elSasen.service;

import org.elSasen.dao.CallDao;
import org.elSasen.dto.insert.CallDtoInsert;
import org.elSasen.dto.select.CallDto;
import org.elSasen.entities.Call;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.CallMapper;
import org.elSasen.validator.CallValidator;
import org.elSasen.validator.Error;
import org.elSasen.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public List<CallDto> getFilterCallTable(String orderBy, Map<String, String> filterMap) {
        var callTable = callDao.getCallTable(orderBy);
        var result = new ArrayList<Call>();
        result = (ArrayList<Call>) callTable;
        for (int i = 0; i < callTable.size();) {
            var call = callTable.get(i);
            if (!filterMap.get("subscriberNumber").isEmpty() && !call.getSubscriberNumber().contains(filterMap.get("subscriberNumber"))) {
                result.remove(i);
            } else if (!filterMap.get("callDurationUp").isEmpty() && !(call.getCallDuration() > Double.parseDouble(filterMap.get("callDurationUp")))) {
                result.remove(i);
            } else if (!filterMap.get("callDurationDown").isEmpty() && !(call.getCallDuration() < Double.parseDouble(filterMap.get("callDurationDown")))) {
                result.remove(i);
            } else if (!filterMap.get("firstName").isEmpty() && !call.getClient().getFirstName().startsWith(filterMap.get("firstName"))) {
                result.remove(i);
            } else if (!filterMap.get("lastName").isEmpty() && !call.getClient().getLastName().startsWith(filterMap.get("lastName"))) {
                result.remove(i);
            } else {
                i++;
            }
        }
        return result.stream()
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

    public void deleteCall(String id){
        if(!callDao.deleteCall(id) ){
            throw new ValidationException(List.of(Error.of("invalid.call", "Звонка с таким идентификатором не существует")));
        }
    }

    public Optional<Integer> getIdOfCall(String id){
        return callDao.getCallById(id);
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
