package org.elSasen.service;

import org.elSasen.dao.ClientDao;
import org.elSasen.dto.ClientDto;
import org.elSasen.mapper.ClientMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientService {

    private static final ClientService INSTANCE = new ClientService();
    private final ClientDao clientDao = ClientDao.getInstance();
    private final ClientMapper clientMapper = ClientMapper.getInstance();

    public Set<ClientDto> getClientTable() {
        var clientTable = clientDao.getClientTable();
        return clientTable.stream()
                .map(clientMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfClient() {
        return clientDao.getMetaData();
    }

    public static ClientService getInstance() {
        return INSTANCE;
    }
}

