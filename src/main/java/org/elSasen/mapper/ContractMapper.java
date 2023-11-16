package org.elSasen.mapper;

import org.elSasen.dto.ContractDto;
import org.elSasen.entities.Contract;

public class ContractMapper implements Mapper<Contract, ContractDto> {

    private static final ContractMapper INSTANCE = new ContractMapper();
    @Override
    public ContractDto mapFrom(Contract contract) {
        return ContractDto.builder()
                .contractId(contract.getContractId())
                .plan(contract.getPlan())
                .client(contract.getClient())
                .date(contract.getDate())
                .build();
    }

    public static ContractMapper getInstance() {
        return INSTANCE;
    }
}
