package org.elSasen.dao;

import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeePassportDao {
    private static final EmployeePassportDao INSTANCE = new EmployeePassportDao();
    private final String DEFAULT_ORDER_BY = "passport_id";

    public List<EmployeePassport> getEmployeePassportTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT passport_id,
                series,
                number,
                birthday,
                issue_date,
                place_code
                FROM employee_passport
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            EmployeePassport employeePassport;
            var employeePassportList = new ArrayList<EmployeePassport>();
            while (resultSet.next()) {
                employeePassport = EmployeePassport.builder()
                        .passportId(resultSet.getInt("passport_id"))
                        .series(resultSet.getString("series"))
                        .number(resultSet.getString("number"))
                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                        .issueDate(resultSet.getObject("issue_date", LocalDate.class))
                        .placeCode(resultSet.getString("place_code"))
                        .build();
                employeePassportList.add(employeePassport);
            }
            return employeePassportList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT passport_id,
                series,
                number,
                birthday,
                issue_date,
                place_code
                FROM employee_passport
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

    public static EmployeePassportDao getInstance() {
        return INSTANCE;
    }
}
