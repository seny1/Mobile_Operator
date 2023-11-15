package org.elSasen.dao;

import org.elSasen.entities.Call;
import org.elSasen.entities.Client;
import org.elSasen.entities.ClientContact;
import org.elSasen.entities.ClientPassport;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.String;

public class CallDao {

    private static final CallDao INSTANCE = new CallDao();

    public Set<Call> getCallTable() {
        String sql = """
                SELECT *
                FROM call
                JOIN public.client c on call.client_id = c.client_id
                JOIN public.client_passport cp on c.passport_id = cp.passport_id
                JOIN public.client_contact cc on c.contact_id = cc.contact_id;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();

            var callSet = new HashSet<Call>();
            Call call;
            while (resultSet.next()) {
                call = Call.builder()
                        .callId(resultSet.getLong("call_id"))
                        .client(Client.builder()
                                .clientId(resultSet.getLong("client_id"))
                                .passport(ClientPassport.builder()
                                        .passportId(resultSet.getLong("passport_id"))
                                        .series(resultSet.getString("series"))
                                        .number(resultSet.getString("number"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .build())
                                .contact(ClientContact.builder()
                                        .contactId(resultSet.getLong("contact_id"))
                                        .number(resultSet.getString("number"))
                                        .type(resultSet.getString("type"))
                                        .build())
                                .build())
                        .subscriberNumber(resultSet.getString("subscriber_number"))
                        .callDuration(resultSet.getDouble("call_duration"))
                        .build();
                callSet.add(call);
            }
            return callSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM call
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

    public static CallDao getInstance() {
        return INSTANCE;
    }
}
