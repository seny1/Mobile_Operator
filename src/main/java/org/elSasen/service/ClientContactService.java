package org.elSasen.service;


import org.elSasen.dao.ClientContactDao;
import org.elSasen.dto.select.ClientContactDto;
import org.elSasen.mapper.ClientContactMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ClientContactService {

    private static final ClientContactService INSTANCE = new ClientContactService();
    private final ClientContactDao clientContactDao = ClientContactDao.getInstance();
    private final ClientContactMapper clientContactMapper = ClientContactMapper.getInstance();

    public List<ClientContactDto> getClientContactTable(String orderBy) {
        var clientContactTable = clientContactDao.getClientContactTable(orderBy);
        return clientContactTable.stream()
                .map(clientContactMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfClientContact() {
        return clientContactDao.getMetaData();
    }

    public static ClientContactService getInstance() {
        return INSTANCE;
    }
}
