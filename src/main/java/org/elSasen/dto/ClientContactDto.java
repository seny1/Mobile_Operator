package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientContactDto {
    long contactId;
    String number;
    String type;
}
