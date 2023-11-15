package org.elSasen.service;

import org.elSasen.dao.CommunicationSalonDao;
import org.elSasen.dto.CommunicationSalonDto;
import org.elSasen.mapper.CommunicationSalonMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommunicationSalonService {

    private static final CommunicationSalonService INSTANCE = new CommunicationSalonService();

    private final CommunicationSalonDao communicationSalonDao = CommunicationSalonDao.getInstance();
    private final CommunicationSalonMapper communicationSalonMapper = CommunicationSalonMapper.getInstance();

    public Set<CommunicationSalonDto> getClientCommunicationSalonTable() {
        var clientContactTable = communicationSalonDao.getCommunicationTable();
        return clientContactTable.stream()
                .map(communicationSalonMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfCommunicationSalon() {
        return communicationSalonDao.getMetaData();
    }


    public static CommunicationSalonService getInstance() {
        return INSTANCE;
    }
}
