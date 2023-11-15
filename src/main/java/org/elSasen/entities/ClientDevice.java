package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDevice {
    int deviceId;
    String model;
    String clientProblem;
}
