package org.elSasen.dao;

import org.elSasen.entities.Status;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatusDao {
    private static final StatusDao INSTANCE = new StatusDao();
    public Set<Status> getStatusTable() {
        String sql = """
                SELECT *
                FROM status
                """;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var statusSet = new HashSet<Status>();
            Status status;
            while (resultSet.next()) {
                status = Status.builder()
                        .statusId(resultSet.getInt("status_id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .build();
                statusSet.add(status);
            }
            return statusSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM status
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

    public static StatusDao getInstance() {
        return INSTANCE;
    }
}
