package org.elSasen.service;

import org.elSasen.dao.CallDao;
import org.elSasen.dto.CallDto;
import org.elSasen.mapper.CallMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CallService {

    private static final CallService INSTANCE = new CallService();

    private final CallDao callDao = CallDao.getInstance();

    private final CallMapper callMapper = CallMapper.getInstance();
    public List<CallDto> getCallTable(String orderBy) {
        var callTable = callDao.getCallTable(orderBy);
        return callTable.stream()
                .map(callMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoCallTable(int clientId, String subscriberNumber, double callDuration) {
        callDao.insertIntoCallTable(clientId, subscriberNumber, callDuration);
    }

    public List<String> getColumnsOfCall() {
        return callDao.getMetaData();
    }

    public static CallService getInstance() {
        return INSTANCE;
    }
}
