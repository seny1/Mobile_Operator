package org.elSasen.service;

import org.elSasen.dao.ClientDeviceDao;
import org.elSasen.dto.ClientDeviceDto;
import org.elSasen.mapper.ClientDeviceMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDeviceService {

    private static final ClientDeviceService INSTANCE = new ClientDeviceService();
    private final ClientDeviceDao clientDeviceDao = ClientDeviceDao.getInstance();
    private final ClientDeviceMapper clientDeviceMapper = ClientDeviceMapper.getInstance();

    public Set<ClientDeviceDto> getClientDeviceTable() {
        var clientDeviceTable = clientDeviceDao.getClientDeviceTable();
        return clientDeviceTable.stream()
                .map(clientDeviceMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfClientDevice() {
        return clientDeviceDao.getMetaData();
    }

    public static ClientDeviceService getInstance() {
        return INSTANCE;
    }
}
