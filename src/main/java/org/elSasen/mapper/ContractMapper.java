package org.elSasen.mapper;

import org.elSasen.dto.insert.ContractDtoInsert;
import org.elSasen.dto.select.ContractDto;
import org.elSasen.entities.Client;
import org.elSasen.entities.Contract;
import org.elSasen.entities.TariffPlan;

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

    public Contract mapFrom(ContractDtoInsert contractDtoInsert) {
        return Contract.builder()
                .client(Client.builder()
                        .clientId(contractDtoInsert.getClientId())
                        .build())
                .plan(TariffPlan.builder()
                        .planName(contractDtoInsert.getPlanName())
                        .build())
                .date(contractDtoInsert.getDate())
                .build();
    }

    public static ContractMapper getInstance() {
        return INSTANCE;
    }
}
