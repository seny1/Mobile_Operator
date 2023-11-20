package org.elSasen.dao;
import org.elSasen.entities.Role;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleDao {
    private static final RoleDao INSTANCE = new RoleDao();
    private final String DEFAULT_ORDER_BY = "role_id";
    public List<Role> getRoleTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT role_id,
                role_name,
                description
                FROM role
                ORDER BY 
                """ + orderBy;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var roleList = new ArrayList<Role>();
            Role role;
            while (resultSet.next()) {
                role = Role.builder()
                        .roleId(resultSet.getInt("role_id"))
                        .roleName(resultSet.getString("role_name"))
                        .description(resultSet.getString("description"))
                        .build();
                roleList.add(role);
            }
            return roleList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT role_id,
                role_name,
                description
                FROM role
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var columnNames = new ArrayList<String>();
            for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }
            return columnNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getRoleIdByRoleName(String roleName) {
        String sql = """
                SELECT role_id
                FROM role
                WHERE role_name = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, roleName);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getRoles() {
        String sql = """
                SELECT role_name
                FROM role;
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var roles = new ArrayList<String>();
            while (resultSet.next()) {
                roles.add(resultSet.getString(1));
            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static RoleDao getInstance() {
        return INSTANCE;
    }
}
