package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;
import org.elSasen.entities.Client;

@Value
@Builder
public class CallDto {
    long callId;
    Client client;
    String subscriberNumber;
    double callDuration;
}
