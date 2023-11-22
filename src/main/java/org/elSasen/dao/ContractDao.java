package org.elSasen.dao;

import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractDao {

    private static final ContractDao INSTANCE = new ContractDao();
    private final String DEFAULT_ORDER_BY = "contract_id";

    public List<Contract> getContractTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT contract_id,
                       tp.plan_id,
                       tp.plan_name,
                       tp.call_minutes,
                       tp.internet_gb,
                       tp.sms_number,
                       tp.price,
                       c.client_id,
                       cp.passport_id,
                       cp.series,
                       cp.number,
                       cp.birthday,
                       c.first_name,
                       c.last_name,
                       cc.contact_id,
                       cc.number,
                       cc.type,
                       date
                FROM contract
                JOIN public.tariff_plan tp on contract.plan_id = tp.plan_id
                JOIN public.client c on contract.client_id = c.client_id
                JOIN public.client_passport cp on c.passport_id = cp.passport_id
                JOIN public.client_contact cc on c.contact_id = cc.contact_id
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            Contract contract;
            var contractList = new ArrayList<Contract>();
            while (resultSet.next()) {
                contract = Contract.builder()
                        .contractId(resultSet.getLong("contract_id"))
                        .plan(TariffPlan.builder()
                                .planId(resultSet.getLong("plan_id"))
                                .planName(resultSet.getString("plan_name"))
                                .callMinutes(resultSet.getInt("call_minutes"))
                                .internetGb(resultSet.getInt("internet_gb"))
                                .smsNumber(resultSet.getInt("sms_number"))
                                .price(resultSet.getInt("price"))
                                .build())
                        .client(Client.builder()
                                .clientId(resultSet.getLong("client_id"))
                                .passport(ClientPassport.builder()
                                        .passportId(resultSet.getLong("passport_id"))
                                        .series(resultSet.getString("series"))
                                        .number(resultSet.getString("number"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .build())
                                .firstName(resultSet.getString("first_name"))
                                .lastName(resultSet.getString("last_name"))
                                .contact(ClientContact.builder()
                                        .contactId(resultSet.getLong("contact_id"))
                                        .number(resultSet.getString("number"))
                                        .type(resultSet.getString("type"))
                                        .build())
                                .build())
                        .date(resultSet.getObject("date", LocalDate.class))
                        .build();
                contractList.add(contract);
            }
            return contractList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM contract;
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

    public void insertIntoContract(Contract contract) {
        String getPlanId = """
                SELECT plan_id
                FROM tariff_plan
                WHERE plan_name = ?;
                """;
        String insertContract = """
                INSERT INTO contract (plan_id, client_id, date)
                VALUES (?, ?, ?);
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatementPlanId = connection.prepareStatement(getPlanId);
        var preparedStatementContract = connection.prepareStatement(insertContract)) {
            preparedStatementPlanId.setString(1, contract.getPlan().getPlanName());
            var resultSet = preparedStatementPlanId.executeQuery();
            resultSet.next();
            var tempPlanId = resultSet.getInt(1);

            preparedStatementContract.setInt(1, tempPlanId);
            preparedStatementContract.setLong(2, contract.getClient().getClientId());
            preparedStatementContract.setObject(3, contract.getDate());
            preparedStatementContract.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ContractDao getInstance() {
        return INSTANCE;
    }
}
