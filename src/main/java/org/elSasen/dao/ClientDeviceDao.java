package org.elSasen.dao;

import org.elSasen.entities.ClientDevice;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;;

public class ClientDeviceDao {

    private static final ClientDeviceDao INSTANCE = new ClientDeviceDao();
    private final String DEFAULT_ORDER_BY = "device_id";

    public List<ClientDevice> getClientDeviceTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT device_id,
                       model,
                       client_problem
                FROM client_device
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            ClientDevice clientDevice;
            var clientDeviceList = new ArrayList<ClientDevice>();
            while (resultSet.next()) {
                clientDevice = ClientDevice.builder()
                        .deviceId(resultSet.getInt("device_id"))
                        .model(resultSet.getString("model"))
                        .clientProblem(resultSet.getString("client_problem"))
                        .build();
                clientDeviceList.add(clientDevice);
            }
            return clientDeviceList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM client_device;
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

    public static ClientDeviceDao getInstance() {
        return INSTANCE;
    }
}
