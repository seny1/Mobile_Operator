package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RoleDto {
    long roleId;
    String roleName;
    String description;
}
