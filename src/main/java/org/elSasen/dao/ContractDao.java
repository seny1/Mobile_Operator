package org.elSasen.dao;

import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContractDao {

    private static final ContractDao INSTANCE = new ContractDao();

    public Set<Contract> getContractTable() {
        String sql = """
                SELECT *
                FROM contract
                JOIN public.tariff_plan tp on contract.plan_id = tp.plan_id
                JOIN public.client c on contract.client_id = c.client_id
                JOIN public.client_passport cp on c.passport_id = cp.passport_id
                JOIN public.client_contact cc on c.contact_id = cc.contact_id;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            Contract contract;
            var contractSet = new HashSet<Contract>();
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
                contractSet.add(contract);
            }
            return contractSet;
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

    public static ContractDao getInstance() {
        return INSTANCE;
    }
}
