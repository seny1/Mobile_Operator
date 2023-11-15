package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientDeviceDto {

    int deviceId;
    String model;
    String clientProblem;
}
