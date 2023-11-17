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
    private final String DEFAULT_ORDER_BY = "status_id";
    public List<Status> getStatusTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT status_id,
                name,
                description
                FROM status
                ORDER BY 
                """ + orderBy;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var statusList = new ArrayList<Status>();
            Status status;
            while (resultSet.next()) {
                status = Status.builder()
                        .statusId(resultSet.getInt("status_id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .build();
                statusList.add(status);
            }
            return statusList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT status_id,
                name,
                description
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
