package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ContractDtoInsert {
    String planName;
    int clientId;
    LocalDate date;
}
