package org.elSasen.mapper;

import org.elSasen.dto.RoleDto;
import org.elSasen.entities.Role;

public class RoleMapper implements Mapper<Role, RoleDto>{
    @Override
    public RoleDto mapFrom(Role role) {
        return RoleDto.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .build();
    }
    private static final RoleMapper INSTANCE = new RoleMapper();
    public static RoleMapper getInstance() {
        return INSTANCE;
    }
}
