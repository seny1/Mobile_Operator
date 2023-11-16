package org.elSasen.dao;
import org.elSasen.entities.Role;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleDao {
    private static final RoleDao INSTANCE = new RoleDao();
    public Set<Role> getRoleTable() {
        String sql = """
                SELECT *
                FROM role
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var roleSet = new HashSet<Role>();
            Role role;
            while (resultSet.next()) {
                role = Role.builder()
                        .roleId(resultSet.getInt("role_id"))
                        .roleName(resultSet.getString("role_name"))
                        .description(resultSet.getString("description"))
                        .build();
                roleSet.add(role);
            }
            return roleSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT *
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

    public static RoleDao getInstance() {
        return INSTANCE;
    }
}
