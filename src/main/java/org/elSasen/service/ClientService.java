package org.elSasen.service;

import org.elSasen.dao.ClientDao;
import org.elSasen.dto.ClientDto;
import org.elSasen.mapper.ClientMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService {

    private static final ClientService INSTANCE = new ClientService();
    private final ClientDao clientDao = ClientDao.getInstance();
    private final ClientMapper clientMapper = ClientMapper.getInstance();

    public List<ClientDto> getClientTable(String orderBy) {
        var clientTable = clientDao.getClientTable(orderBy);
        return clientTable.stream()
                .map(clientMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfClient() {
        return clientDao.getMetaData();
    }

    public int insertIntoClientTable(String first_name, String last_name, String series, String numberOfPassport, LocalDate birthday, String numberOfContact, String type) {
        return clientDao.insertIntoClientTable(first_name, last_name, series, numberOfPassport, birthday, numberOfContact, type);
    }

    public static ClientService getInstance() {
        return INSTANCE;
    }
}

