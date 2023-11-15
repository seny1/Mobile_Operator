package org.elSasen.dao;

import org.elSasen.entities.CommunicationSalon;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommunicationSalonDao {

    private static final CommunicationSalonDao INSTANCE = new CommunicationSalonDao();

    public Set<CommunicationSalon> getCommunicationTable() {
        String sql = """
                SELECT *
                FROM communication_salon;
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            CommunicationSalon communicationSalon;
            var communicationSalonSet = new HashSet<CommunicationSalon>();
            while (resultSet.next()) {
                communicationSalon = CommunicationSalon.builder()
                        .salonId(resultSet.getLong("salon_id"))
                        .address(resultSet.getString("address"))
                        .employeeNumber(resultSet.getInt("employee_number"))
                        .build();
                communicationSalonSet.add(communicationSalon);
            }
            return communicationSalonSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM communication_salon;
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

    public static CommunicationSalonDao getInstance() {
        return INSTANCE;
    }
}
