package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientDeviceDto {

    int deviceId;
    String model;
    String clientProblem;
}
