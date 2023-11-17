package org.elSasen.dao;

import org.elSasen.entities.ClientContact;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientContactDao {

    private static final ClientContactDao INSTANCE = new ClientContactDao();
    private final String DEFAULT_ORDER_BY = "contact_id";

    public List<ClientContact> getClientContactTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT contact_id,
                       number,
                       type
                FROM client_contact
                ORDER BY
                """ + orderBy;
        try (Connection connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            ClientContact clientContact;
            var clientContactList = new ArrayList<ClientContact>();
            while (resultSet.next()) {
                clientContact = ClientContact.builder()
                        .contactId(resultSet.getLong("contact_id"))
                        .number(resultSet.getString("number"))
                        .type(resultSet.getString("type"))
                        .build();
                clientContactList.add(clientContact);
            }
            return clientContactList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM client_contact;
                """;
        var columnNames = new ArrayList<String>();
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }
            return columnNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClientContactDao getInstance() {
        return INSTANCE;
    }
}
