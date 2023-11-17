package org.elSasen.dao;

import org.elSasen.entities.ClientPassport;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientPassportDao {

    private static final ClientPassportDao INSTANCE = new ClientPassportDao();
    private final String DEFAULT_ORDER_BY = "passport_id";

    public List<ClientPassport> getClientPassportTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT passport_id,
                       series,
                       number,
                       birthday
                FROM client_passport
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            ClientPassport clientPassport;
            var clientPassportList = new ArrayList<ClientPassport>();
            while (resultSet.next()) {
                clientPassport = ClientPassport.builder()
                        .passportId(resultSet.getLong("passport_id"))
                        .series(resultSet.getString("series"))
                        .number(resultSet.getString("number"))
                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                        .build();
                clientPassportList.add(clientPassport);
            }
            return clientPassportList;
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
