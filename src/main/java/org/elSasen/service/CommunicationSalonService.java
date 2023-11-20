package org.elSasen.service;

import org.elSasen.dao.CommunicationSalonDao;
import org.elSasen.dto.CommunicationSalonDto;
import org.elSasen.mapper.CommunicationSalonMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CommunicationSalonService {

    private static final CommunicationSalonService INSTANCE = new CommunicationSalonService();

    private final CommunicationSalonDao communicationSalonDao = CommunicationSalonDao.getInstance();
    private final CommunicationSalonMapper communicationSalonMapper = CommunicationSalonMapper.getInstance();

    public List<CommunicationSalonDto> getClientCommunicationSalonTable(String orderBy) {
        var clientContactTable = communicationSalonDao.getCommunicationTable(orderBy);
        return clientContactTable.stream()
                .map(communicationSalonMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfCommunicationSalon() {
        return communicationSalonDao.getMetaData();
    }

    public List<String> getSalons() {
        return communicationSalonDao.getAddresses();
    }
    public static CommunicationSalonService getInstance() {
        return INSTANCE;
    }
}
