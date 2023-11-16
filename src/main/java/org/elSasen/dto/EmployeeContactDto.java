package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeContactDto {

    int contactId;
    String workNumber;
    String personalNumber;
}
