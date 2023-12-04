package org.elSasen.dao;

import org.elSasen.entities.Client;
import org.elSasen.entities.ClientContact;
import org.elSasen.entities.ClientPassport;
import org.elSasen.util.ConnectionManager;

import javax.swing.text.html.parser.Entity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class ClientDao {

    private static final ClientDao INSTANCE = new ClientDao();

    private final String DEFAULT_ORDER_BY = "client_id";
    private final ClientPassportDao clientPassportDao = ClientPassportDao.getInstance();

    public List<Client> getClientTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT client_id,
                       cp.passport_id,
                       birthday,
                       cp.number as number_of_passport,
                       series,
                       first_name,
                       last_name,
                       cc.contact_id,
                       cc.number as number_of_contact,
                       type,
                       remain_minutes
                FROM client
                JOIN public.client_passport cp on cp.passport_id = client.passport_id
                JOIN public.client_contact cc on client.contact_id = cc.contact_id
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            Client client;
            var clientList = new ArrayList<Client>();
            while (resultSet.next()) {
                client = buildClient(resultSet);
                clientList.add(client);
            }
            return clientList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertIntoClientTable(Client client) {
        String sqlInsertPassportContact = """
                INSERT INTO client_passport (series, number, birthday)
                VALUES (?, ?, ?);
                INSERT INTO client_contact (number, type)
                VALUES (?, ?);
                """;
        String sqlGetPassportId = """
                SELECT passport_id
                FROM client_passport
                WHERE series = ? AND number = ?;
                """;
        String sqlGetContactId = """
                SELECT contact_id
                FROM client_contact
                WHERE number = ?;
                """;
        String sqlInsertClient = """
                INSERT INTO client (passport_id, first_name, last_name, contact_id)
                VALUES (?, ?, ?, ?);
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatementPassCont = connection.prepareStatement(sqlInsertPassportContact);
             var preparedStatementGetPass = connection.prepareStatement(sqlGetPassportId);
             var preparedStatementGetCont = connection.prepareStatement(sqlGetContactId);
             var preparedStatementClient = connection.prepareStatement(sqlInsertClient, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatementPassCont.setString(1, client.getPassport().getSeries());
            preparedStatementPassCont.setString(2, client.getPassport().getNumberOfPassport());
            preparedStatementPassCont.setObject(3, client.getPassport().getBirthday());
            preparedStatementPassCont.setString(4, client.getContact().getNumberOfContact());
            preparedStatementPassCont.setString(5, client.getContact().getType());
            preparedStatementPassCont.executeUpdate();

            preparedStatementGetPass.setString(1, client.getPassport().getSeries());
            preparedStatementGetPass.setString(2, client.getPassport().getNumberOfPassport());
            var resultSet = preparedStatementGetPass.executeQuery();
            resultSet.next();
            int tempPassportId = resultSet.getInt("passport_id");

            preparedStatementGetCont.setString(1, client.getContact().getNumberOfContact());
            resultSet = preparedStatementGetCont.executeQuery();
            resultSet.next();
            int tempContactId = resultSet.getInt("contact_id");

            preparedStatementClient.setInt(1, tempPassportId);
            preparedStatementClient.setString(2, client.getFirstName());
            preparedStatementClient.setString(3, client.getLastName());
            preparedStatementClient.setInt(4, tempContactId);
            preparedStatementClient.executeUpdate();

            var generatedKeys = preparedStatementClient.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt("client_id");
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
                       contact_id,
                       remain_minutes
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

    public List<String> getGoodMetaData() {
        String sql = """
                SELECT first_name,
                       last_name,
                       series,
                       cp.number AS number_of_passport,
                       birthday,
                       cc.number AS number_of_contact,
                       type
                FROM client
                JOIN public.client_passport cp on cp.passport_id = client.passport_id
                JOIN public.client_contact cc on client.contact_id = cc.contact_id;
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

    public Optional<Client> findClientById(int clientId) {
        String sql = """
                SELECT client_id,
                       cp.passport_id,
                       birthday,
                       cp.number as number_of_passport,
                       series,
                       first_name,
                       last_name,
                       cc.contact_id,
                       cc.number as number_of_contact,
                       type,
                       remain_minutes
                FROM client
                JOIN public.client_passport cp on cp.passport_id = client.passport_id
                JOIN public.client_contact cc on client.contact_id = cc.contact_id
                WHERE client_id = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientId);
            var resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            var client = buildClient(resultSet);
            return Optional.ofNullable(client);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteClient(String series, String numberOfPassport) {
        String sql = """
                DELETE FROM client WHERE passport_id = ?;
                """;
        var passportId = clientPassportDao.getPassportBySeriesNumber(series, numberOfPassport);
        if (passportId.isEmpty()) {
            return false;
        }
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, passportId.get());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Client buildClient(ResultSet resultSet) throws SQLException {
        return Client.builder()
                .clientId(resultSet.getLong("client_id"))
                .passport(ClientPassport.builder()
                        .passportId(resultSet.getLong("passport_id"))
                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                        .numberOfPassport(resultSet.getString("number_of_passport"))
                        .series(resultSet.getString("series"))
                        .build())
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .contact(ClientContact.builder()
                        .contactId(resultSet.getLong("contact_id"))
                        .numberOfContact(resultSet.getString("number_of_contact"))
                        .type(resultSet.getString("type"))
                        .build())
                .remainMinutes(resultSet.getInt("remain_minutes"))
                .build();
    }
    public static ClientDao getInstance() {
        return INSTANCE;
    }
}
