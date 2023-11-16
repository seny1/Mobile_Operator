package org.elSasen.service;


import org.elSasen.dao.ClientContactDao;
import org.elSasen.dto.ClientContactDto;
import org.elSasen.mapper.ClientContactMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientContactService {

    private static final ClientContactService INSTANCE = new ClientContactService();
    private final ClientContactDao clientContactDao = ClientContactDao.getInstance();
    private final ClientContactMapper clientContactMapper = ClientContactMapper.getInstance();

    public Set<ClientContactDto> getClientContactTable() {
        var clientContactTable = clientContactDao.getClientContactTable();
        return clientContactTable.stream()
                .map(clientContactMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfClientContact() {
        return clientContactDao.getMetaData();
    }

    public static ClientContactService getInstance() {
        return INSTANCE;
    }
}
