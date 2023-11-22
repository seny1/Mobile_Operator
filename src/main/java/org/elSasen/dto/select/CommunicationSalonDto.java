package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommunicationSalonDto {
    long salonId;
    String address;
    int employeeNumber;
}
