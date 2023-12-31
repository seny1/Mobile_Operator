package org.elSasen.dao;

import org.elSasen.entities.CommunicationSalon;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommunicationSalonDao {

    private static final CommunicationSalonDao INSTANCE = new CommunicationSalonDao();
    private final String DEFAULT_ORDER_BY = "salon_id";

    public List<CommunicationSalon> getCommunicationTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT *
                FROM communication_salon
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            CommunicationSalon communicationSalon;
            var communicationSalonList = new ArrayList<CommunicationSalon>();
            while (resultSet.next()) {
                communicationSalon = CommunicationSalon.builder()
                        .salonId(resultSet.getLong("salon_id"))
                        .address(resultSet.getString("address"))
                        .employeeNumber(resultSet.getInt("employee_number"))
                        .build();
                communicationSalonList.add(communicationSalon);
            }
            return communicationSalonList;
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

    public int getSalonIdByAddress(String address) {
        String sql = """
                SELECT salon_id
                FROM communication_salon
                WHERE address = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAddresses() {
        String sql = """
                SELECT address
                FROM communication_salon;
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var addresses = new ArrayList<String>();
            while (resultSet.next()) {
                addresses.add(resultSet.getString(1));
            }
            return addresses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static CommunicationSalonDao getInstance() {
        return INSTANCE;
    }
}
