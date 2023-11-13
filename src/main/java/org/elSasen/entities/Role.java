package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Role {
    long roleId;
    String roleName;
    String description;
}
