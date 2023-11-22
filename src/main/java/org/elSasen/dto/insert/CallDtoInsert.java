package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CallDtoInsert {
    int clientId;
    String subscriberNumber;
    double callDuration;
}
