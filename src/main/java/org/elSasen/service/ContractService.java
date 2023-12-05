package org.elSasen.service;

import org.elSasen.dao.ContractDao;
import org.elSasen.dto.insert.ContractDtoInsert;
import org.elSasen.dto.select.CallDto;
import org.elSasen.dto.select.ContractDto;
import org.elSasen.entities.Call;
import org.elSasen.entities.Contract;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ContractMapper;
import org.elSasen.validator.ContractValidator;
import org.elSasen.validator.Error;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<ContractDto> getFilterContractTable(String orderBy, Map<String, String> filterMap) {
        var contractTable = contractDao.getContractTable(orderBy);
        var result = new ArrayList<Contract>();
        result = (ArrayList<Contract>) contractTable;
        for (int i = 0; i < contractTable.size();) {
            var contract = contractTable.get(i);
            if (!filterMap.get("planName").isEmpty() && !contract.getPlan().getPlanName().equals(filterMap.get("planName"))) {
                result.remove(i);
            } else if (!filterMap.get("dateUp").isEmpty() && contract.getDate().isBefore(LocalDate.parse(filterMap.get("dateUp")))) {
                result.remove(i);
            } else if (!filterMap.get("dateDown").isEmpty() && contract.getDate().isAfter(LocalDate.parse(filterMap.get("dateDown")))) {
                result.remove(i);
            } else if (!filterMap.get("number").isEmpty() && !contract.getClient().getContact().getNumberOfContact().equals(filterMap.get("number"))) {
                result.remove(i);
            } else if (!filterMap.get("firstName").isEmpty() && !contract.getClient().getFirstName().equals(filterMap.get("firstName"))) {
                result.remove(i);
            } else if (!filterMap.get("lastName").isEmpty() && !contract.getClient().getFirstName().equals(filterMap.get("lastName"))) {
                result.remove(i);
            } else {
                i++;
            }
        }
        return result.stream()
                .map(contractMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void deleteContract(int contractId) {
        if (!contractDao.deleteContract(contractId)) {
            throw new ValidationException(List.of(Error.of("invalid.contract", "Контракта с таким id не существует")));
        }
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
    public List<String> getGoodColumnsOfContract() {
        return contractDao.getGoodMetaData();
    }

    public static ContractService getInstance() {
        return INSTANCE;
    }
}
