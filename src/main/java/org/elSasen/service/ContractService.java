package org.elSasen.service;

import org.elSasen.dao.ContractDao;
import org.elSasen.dto.insert.ContractDtoInsert;
import org.elSasen.dto.select.ContractDto;
import org.elSasen.entities.Contract;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ContractMapper;
import org.elSasen.validator.ContractValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ContractService {

    private static final ContractService INSTANCE = new ContractService();
    private final ContractDao contractDao = ContractDao.getInstance();
    private final ContractMapper contractMapper = ContractMapper.getInstance();
    private final ContractValidator contractValidator = ContractValidator.getInstance();

    public List<ContractDto> getContractTable(String orderBy) {
        var contractTable = contractDao.getContractTable(orderBy);
        return contractTable.stream()
                .map(contractMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void insertIntoContract(ContractDtoInsert contractDtoInsert) {
        var validationResult = contractValidator.isValid(contractDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var contract = contractMapper.mapFrom(contractDtoInsert);
        contractDao.insertIntoContract(contract);
    }

    public List<String> getColumnsOfContract() {
        return contractDao.getMetaData();
    }

    public static ContractService getInstance() {
        return INSTANCE;
    }
}
