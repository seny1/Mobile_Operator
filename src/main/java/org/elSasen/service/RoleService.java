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

    public Set<RoleDto> getRoleTable() {
        var roleTable = roleDao.getRoleTable();
        return roleTable.stream()
                .map(roleMapper::mapFrom)
                .collect(Collectors.toSet());
    }

    public List<String> getColumnsOfRole() {
        return roleDao.getMetaData();
    }

    public static RoleService getInstance() {
        return INSTANCE;
    }
}
