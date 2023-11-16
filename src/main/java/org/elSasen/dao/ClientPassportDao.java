package org.elSasen.dao;

import org.elSasen.entities.ClientPassport;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientPassportDao {

    private static final ClientPassportDao INSTANCE = new ClientPassportDao();

    public Set<ClientPassport> getClientPassportTable() {
        String sql = """
                SELECT *
                FROM client_passport;
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            ClientPassport clientPassport;
            var clientPassportSet = new HashSet<ClientPassport>();
            while (resultSet.next()) {
                clientPassport = ClientPassport.builder()
                        .passportId(resultSet.getLong("passport_id"))
                        .series(resultSet.getString("series"))
                        .number(resultSet.getString("number"))
                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                        .build();
                clientPassportSet.add(clientPassport);
            }
            return clientPassportSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM client_passport;
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

    public static ClientPassportDao getInstance() {
        return INSTANCE;
    }
}
