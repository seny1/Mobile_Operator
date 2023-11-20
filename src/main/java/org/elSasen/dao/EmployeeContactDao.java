package org.elSasen.dao;

import org.elSasen.entities.EmployeeContact;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeContactDao {

    private static final EmployeeContactDao INSTANCE = new EmployeeContactDao();
    private final String DEFAULT_ORDER_BY = "contact_id";

    public List<EmployeeContact> getEmployeeContactTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT contact_id,
                       work_number,
                       personal_number
                FROM employee_contact
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            EmployeeContact employeeContact;
            var employeeContactList = new ArrayList<EmployeeContact>();
            while (resultSet.next()) {
                employeeContact = EmployeeContact.builder()
                        .contactId(resultSet.getInt("contact_id"))
                        .workNumber(resultSet.getString("work_number"))
                        .personalNumber(resultSet.getString("personal_number"))
                        .build();
                employeeContactList.add(employeeContact);
            }
            return employeeContactList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM employee_contact;
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

    public int insertIntoContactAndReturnId(String workNumber, String personalNumber) {
        String sql = """
                INSERT INTO employee_contact (work_number, personal_number)
                VALUES (?, ?);
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, workNumber);
            preparedStatement.setString(2, personalNumber);

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt("contact_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static EmployeeContactDao getInstance() {
        return INSTANCE;
    }
}
