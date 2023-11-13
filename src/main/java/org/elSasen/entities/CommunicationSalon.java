package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunicationSalon {
    long salonId;
    String address;
    int employeeNumber;
}
