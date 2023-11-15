package org.elSasen.service;

import org.elSasen.dao.ClientPassportDao;
import org.elSasen.dto.ClientPassportDto;
import org.elSasen.mapper.ClientPassportMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientPassportService {

    private static final ClientPassportService INSTANCE = new ClientPassportService();

    private final ClientPassportDao clientPassportDao = ClientPassportDao.getInstance();

    private final ClientPassportMapper clientPassportMapper = ClientPassportMapper.getInstance();

    public Set<ClientPassportDto> getClientContactTable() {
        var clientPassportTable = clientPassportDao.getClientPassportTable();
        return clientPassportTable.stream()
                .map(clientPassportMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfClientPassport() {
        return clientPassportDao.getMetaData();
    }
    public static ClientPassportService getInstance() {
        return INSTANCE;
    }
}
