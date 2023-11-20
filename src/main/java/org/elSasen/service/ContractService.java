package org.elSasen.service;

import org.elSasen.dao.ContractDao;
import org.elSasen.dto.ContractDto;
import org.elSasen.mapper.ContractMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ContractService {

    private static final ContractService INSTANCE = new ContractService();
    private final ContractDao contractDao = ContractDao.getInstance();
    private final ContractMapper contractMapper = ContractMapper.getInstance();

    public List<ContractDto> getContractTable(String orderBy) {
        var contractTable = contractDao.getContractTable(orderBy);
        return contractTable.stream()
                .map(contractMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoContract(String planName, int clientId, LocalDate date) {
        contractDao.insertIntoContract(planName, clientId, date);
    }

    public List<String> getColumnsOfContract() {
        return contractDao.getMetaData();
    }

    public static ContractService getInstance() {
        return INSTANCE;
    }
}
