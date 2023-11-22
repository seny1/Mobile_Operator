package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CheckDtoInsert {

    String[] productName;
    Integer[] productCount;
    int clientId;
}
