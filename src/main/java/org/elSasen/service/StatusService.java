package org.elSasen.service;
import org.elSasen.dao.StatusDao;
import org.elSasen.dto.select.StatusDto;
import org.elSasen.mapper.StatusMapper;

import java.util.List;
import java.util.stream.Collectors;

public class StatusService {
    private static final StatusService INSTANCE = new StatusService();
    private final StatusDao statusDao = StatusDao.getInstance();
    private final StatusMapper statusMapper = StatusMapper.getInstance();

    public List<StatusDto> getStatusTable(String orderBy) {
        var statusTable = statusDao.getStatusTable(orderBy);
        return statusTable.stream()
                .map(statusMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfStatus() {
        return statusDao.getMetaData();
    }

    public static StatusService getInstance() {
        return INSTANCE;
    }
}
