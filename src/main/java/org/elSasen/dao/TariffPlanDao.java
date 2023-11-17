package org.elSasen.dao;

import org.elSasen.entities.TariffPlan;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static TariffPlanDao getInstance() {
        return INSTANCE;
    }
}
