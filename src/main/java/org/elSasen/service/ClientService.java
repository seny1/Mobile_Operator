package org.elSasen.service;

import org.elSasen.dao.ClientDao;
import org.elSasen.dto.insert.ClientDtoInsert;
import org.elSasen.dto.select.ClientDto;
import org.elSasen.entities.Client;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ClientMapper;
import org.elSasen.validator.ClientValidator;
import org.elSasen.validator.Error;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<ClientDto> getFilterClientTable(String orderBy, Map<String, String> filterMap) {
        var clientTable = clientDao.getClientTable(orderBy);
        var results = new ArrayList<Client>();
        results = (ArrayList<Client>) clientTable;
        for (int i = 0; i < clientTable.size();) {
            var client = clientTable.get(i);
            if (!filterMap.get("firstName").isEmpty() && !client.getFirstName().startsWith(filterMap.get("firstName"))) {
                results.remove(i);
            } else if (!filterMap.get("lastName").isEmpty() && !client.getLastName().startsWith(filterMap.get("lastName"))) {
                results.remove(i);
            } else if (!filterMap.get("series").isEmpty() && !client.getPassport().getSeries().startsWith(filterMap.get("series"))) {
                results.remove(i);
            } else if (!filterMap.get("numberOfPassport").isEmpty() && !client.getPassport().getNumberOfPassport().startsWith(filterMap.get("numberOfPassport"))) {
                results.remove(i);
            } else if (!filterMap.get("birthdayUp").isEmpty() && client.getPassport().getBirthday().isBefore(LocalDate.parse(filterMap.get("birthdayUp")))) {
                results.remove(i);
            } else if (!filterMap.get("birthdayDown").isEmpty() && client.getPassport().getBirthday().isAfter(LocalDate.parse(filterMap.get("birthdayDown")))) {
                results.remove(i);
            } else if (!filterMap.get("numberOfContact").isEmpty() && !client.getContact().getNumberOfContact().contains(filterMap.get("numberOfContact"))) {
                results.remove(i);
            } else if (!filterMap.get("type").isEmpty() && !client.getContact().getType().equals(filterMap.get("type"))) {
                results.remove(i);
            } else {
                i++;
            }
        }
        return results.stream()
                .map(clientMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfClient() {
        return clientDao.getMetaData();
    }

    public List<String> getGoodColumnsOfClient() {
        return clientDao.getGoodMetaData();
    }
    public int insertIntoClientTable(ClientDtoInsert clientDtoInsert) {
        var validationResult = clientValidator.isValid(clientDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var client = clientMapper.mapFrom(clientDtoInsert);
        return clientDao.insertIntoClientTable(client);
    }

    public void deleteClient(String series, String number) {
        if (!clientDao.deleteClient(series, number)) {
            throw new ValidationException(List.of(Error.of("invalid.passport", "Клиента с таким паспортом не существует")));
        }
    }
    public Optional<ClientDto> getClientById(int id) {
        return clientDao.findClientById(id)
                .map(clientMapper::mapFrom);
    }

    public static ClientService getInstance() {
        return INSTANCE;
    }
}

