package org.elSasen.dao;

import org.elSasen.entities.Client;
import org.elSasen.entities.ClientContact;
import org.elSasen.entities.ClientPassport;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ClientDao {

    private static final ClientDao INSTANCE = new ClientDao();

    private final String DEFAULT_ORDER_BY = "client_id";

    public List<Client> getClientTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT client_id,
                       cp.passport_id,
                       birthday,
                       cp.number,
                       series,
                       first_name,
                       last_name,
                       cc.contact_id,
                       cc.number,
                       type
                FROM client
                JOIN public.client_passport cp on cp.passport_id = client.passport_id
                JOIN public.client_contact cc on client.contact_id = cc.contact_id
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            Client client;
            var clientSet = new ArrayList<Client>();
            while (resultSet.next()) {
                client = Client.builder()
                        .clientId(resultSet.getLong("client_id"))
                        .passport(ClientPassport.builder()
                                .passportId(resultSet.getLong("passport_id"))
                                .birthday(resultSet.getObject("birthday", LocalDate.class))
                                .number(resultSet.getString("number"))
                                .series(resultSet.getString("series"))
                                .build())
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .contact(ClientContact.builder()
                                .contactId(resultSet.getLong("contact_id"))
                                .number(resultSet.getString("number"))
                                .type(resultSet.getString("type"))
                                .build())
                        .build();
                clientSet.add(client);
            }
            return clientSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT client_id,
                       passport_id,
                       first_name,
                       last_name,
                       contact_id
                FROM client;
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

    public static ClientDao getInstance() {
        return INSTANCE;
    }
}
