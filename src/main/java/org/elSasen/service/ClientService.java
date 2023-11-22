package org.elSasen.service;

import org.elSasen.dao.ClientDao;
import org.elSasen.dto.insert.ClientDtoInsert;
import org.elSasen.dto.select.ClientDto;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ClientMapper;
import org.elSasen.validator.ClientValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService {

    private static final ClientService INSTANCE = new ClientService();
    private final ClientDao clientDao = ClientDao.getInstance();
    private final ClientValidator clientValidator = ClientValidator.getInstance();
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

    public int insertIntoClientTable(ClientDtoInsert clientDtoInsert) {
        var validationResult = clientValidator.isValid(clientDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var client = clientMapper.mapFrom(clientDtoInsert);
        return clientDao.insertIntoClientTable(client);
    }

    public Optional<ClientDto> getClientById(int id) {
        return clientDao.findClientById(id)
                .map(clientMapper::mapFrom);
    }

    public static ClientService getInstance() {
        return INSTANCE;
    }
}

