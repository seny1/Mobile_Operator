package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Status {
    int statusId;
    String name;
    String description;
}
