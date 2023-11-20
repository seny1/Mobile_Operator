package org.elSasen.service;

import org.elSasen.dao.RoleDao;
import org.elSasen.dto.RoleDto;
import org.elSasen.mapper.RoleMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleService {
    private static final RoleService INSTANCE = new RoleService();
    private final RoleDao roleDao = RoleDao.getInstance();
    private final RoleMapper roleMapper = RoleMapper.getInstance();

    public List<RoleDto> getRoleTable(String orderBy) {
        var roleTable = roleDao.getRoleTable(orderBy);
        return roleTable.stream()
                .map(roleMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfRole() {
        return roleDao.getMetaData();
    }

    public List<String> getRoles() {
        return roleDao.getRoles();
    }
    public static RoleService getInstance() {
        return INSTANCE;
    }
}
