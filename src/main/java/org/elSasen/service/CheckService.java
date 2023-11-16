package org.elSasen.service;

import org.elSasen.dao.CheckDao;
import org.elSasen.dto.CheckDto;
import org.elSasen.mapper.CheckMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckService {

    private static final CheckService INSTANCE = new CheckService();
    private final CheckDao checkDao = CheckDao.getInstance();
    private final CheckMapper checkMapper = CheckMapper.getInstance();

    public Set<CheckDto> getCheckTable() {
        var checkTable = checkDao.getCheckTable();
        return checkTable.stream()
                .map(checkMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfCheck() {
        return checkDao.getMetaData();
    }

    public static CheckService getInstance() {
        return INSTANCE;
    }
}
