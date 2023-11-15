package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Call {
    long callId;
    Client client;
    String subscriberNumber;
    double callDuration;
}
