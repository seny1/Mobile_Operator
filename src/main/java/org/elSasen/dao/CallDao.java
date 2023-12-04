package org.elSasen.dao;

import org.elSasen.dto.insert.CallDtoInsert;
import org.elSasen.entities.Call;
import org.elSasen.entities.Client;
import org.elSasen.entities.ClientContact;
import org.elSasen.entities.ClientPassport;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.Optional;

public class CallDao {

    private static final CallDao INSTANCE = new CallDao();
    private final String DEFAULT_ORDER_BY = "call_id";

    public List<Call> getCallTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT call_id,
                       c.client_id,
                       cp.passport_id,
                       cp.series,
                       cp.number,
                       cp.birthday,
                       first_name,
                       last_name,
                       cc.contact_id,
                       cc.number,
                       cc.type,
                       subscriber_number,
                       call_duration
                FROM call
                JOIN public.client c on call.client_id = c.client_id
                JOIN public.client_passport cp on c.passport_id = cp.passport_id
                JOIN public.client_contact cc on c.contact_id = cc.contact_id
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();

            var callList = new ArrayList<Call>();
            Call call;
            while (resultSet.next()) {
                call = Call.builder()
                        .callId(resultSet.getLong("call_id"))
                        .client(Client.builder()
                                .clientId(resultSet.getLong("client_id"))
                                .passport(ClientPassport.builder()
                                        .passportId(resultSet.getLong("passport_id"))
                                        .series(resultSet.getString("series"))
                                        .numberOfPassport(resultSet.getString("number"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .build())
                                .firstName(resultSet.getString("first_name"))
                                .lastName(resultSet.getString("last_name"))
                                .contact(ClientContact.builder()
                                        .contactId(resultSet.getLong("contact_id"))
                                        .numberOfContact(resultSet.getString("number"))
                                        .type(resultSet.getString("type"))
                                        .build())
                                .build())
                        .subscriberNumber(resultSet.getString("subscriber_number"))
                        .callDuration(resultSet.getDouble("call_duration"))
                        .build();
                callList.add(call);
            }
            return callList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM call;
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
    public boolean deleteCall(String id){
        String sql = """
            DELETE FROM call WHERE call_id = ?
            """;
        var callId = getCallById(id);
        if(callId.isEmpty()){
            return false;
        }
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, callId.get());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    };
    public Optional<Integer> getCallById(String id){
        String sql = """
            SELECT call_id FROM call
            WHERE call_id = ?
            """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            var resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(resultSet.getInt("call_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e){
            return Optional.empty();
        }
    }
    public List<String> getGoodMetaData() {
        String sql = """
                SELECT call_id,
                subscriber_number,
                call_duration,
                first_name,
                last_name
                FROM call
                join client on call.client_id = client.client_id;
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
    public void insertIntoCallTable(Call call) {
        String sql = """
                INSERT INTO call (client_id, subscriber_number, call_duration)
                VALUES (?, ?, ?)
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, call.getClient().getClientId());
            preparedStatement.setString(2, call.getSubscriberNumber());
            preparedStatement.setDouble(3, call.getCallDuration());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CallDao getInstance() {
        return INSTANCE;
    }
}
