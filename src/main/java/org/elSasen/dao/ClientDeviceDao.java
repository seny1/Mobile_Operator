package org.elSasen.dao;

import org.elSasen.entities.ClientDevice;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientDeviceDao {

    private static final ClientDeviceDao INSTANCE = new ClientDeviceDao();

    public Set<ClientDevice> getClientDeviceTable() {
        String sql = """
                SELECT *
                FROM client_device;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            ClientDevice clientDevice;
            var clientDeviceSet = new HashSet<ClientDevice>();
            while (resultSet.next()) {
                clientDevice = ClientDevice.builder()
                        .deviceId(resultSet.getInt("device_id"))
                        .model(resultSet.getString("model"))
                        .clientProblem(resultSet.getString("client_problem"))
                        .build();
                clientDeviceSet.add(clientDevice);
            }
            return clientDeviceSet;
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
