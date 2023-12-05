package org.elSasen.dao;

import org.elSasen.entities.TariffPlan;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TariffPlanDao {
    private static final TariffPlanDao INSTANCE = new TariffPlanDao();
    private final String DEFAULT_ORDER_BY = "plan_id";
    public List<TariffPlan> getTariffPlanTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT plan_id,
                plan_name,
                call_minutes,
                internet_gb,
                sms_number,
                price
                FROM tariff_plan
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var tariffPlanList = new ArrayList<TariffPlan>();
            TariffPlan tariffPlan;
            while (resultSet.next()) {
                tariffPlan = TariffPlan.builder()
                        .planId(resultSet.getInt("plan_id"))
                        .planName(resultSet.getString("plan_name"))
                        .callMinutes(resultSet.getInt("call_minutes"))
                        .internetGb(resultSet.getInt("internet_gb"))
                        .smsNumber(resultSet.getInt("sms_number"))
                        .price(resultSet.getInt("price"))
                        .build();
                tariffPlanList.add(tariffPlan);
            }
            return tariffPlanList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT plan_id,
                plan_name,
                call_minutes,
                internet_gb,
                sms_number,
                price
                FROM tariff_plan
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

    public List<String> getPlans() {
        String sql = """
                SELECT plan_name
                FROM tariff_plan;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var plans = new ArrayList<String>();
            while (resultSet.next()) {
                plans.add(resultSet.getString(1));
            }
            return plans;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteTariff(String name){
        String sql = """
            DELETE FROM tariff_plan WHERE plan_name = ?
            """;
        var planName = getPlanName(name);
        if(planName.isEmpty()){
            return false;
        }
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, planName.get());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<String> getPlanName(String name){
        String sql = """
            SELECT plan_name FROM tariff_plan WHERE plan_name = ?
            """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(resultSet.getString("plan_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertIntoTariffPlan(TariffPlan tariffPlan) {
        String sql = """
                INSERT INTO tariff_plan (plan_name, call_minutes, internet_gb, sms_number, price)
                VALUES (?, ?, ?, ?, ?);
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tariffPlan.getPlanName());
            preparedStatement.setInt(2, tariffPlan.getCallMinutes());
            preparedStatement.setInt(3, tariffPlan.getInternetGb());
            preparedStatement.setInt(4, tariffPlan.getSmsNumber());
            preparedStatement.setInt(5, tariffPlan.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static TariffPlanDao getInstance() {
        return INSTANCE;
    }
}
