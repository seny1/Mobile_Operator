package org.elSasen.service;

import org.elSasen.dao.CheckDao;
import org.elSasen.dto.CheckDto;
import org.elSasen.mapper.CheckMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CheckService {

    private static final CheckService INSTANCE = new CheckService();
    private final CheckDao checkDao = CheckDao.getInstance();
    private final CheckMapper checkMapper = CheckMapper.getInstance();

    public List<CheckDto> getCheckTable(String orderBy) {
        var checkTable = checkDao.getCheckTable(orderBy);
        return checkTable.stream()
                .map(checkMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfCheck() {
        return checkDao.getMetaData();
    }

    public static CheckService getInstance() {
        return INSTANCE;
    }
}
