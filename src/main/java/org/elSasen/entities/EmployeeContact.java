package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeContact {
    int contactId;
    String workNumber;
    String personalNumber;
}
