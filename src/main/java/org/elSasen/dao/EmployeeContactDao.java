package org.elSasen.dao;

import org.elSasen.entities.EmployeeContact;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeContactDao {

    private static final EmployeeContactDao INSTANCE = new EmployeeContactDao();

    public Set<EmployeeContact> getEmployeeContactTable() {
        String sql = """
                SELECT *
                FROM employee_contact
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            EmployeeContact employeeContact;
            var employeeContactSet = new HashSet<EmployeeContact>();
            while (resultSet.next()) {
                employeeContact = EmployeeContact.builder()
                        .contactId(resultSet.getInt("contact_id"))
                        .workNumber(resultSet.getString("work_number"))
                        .personalNumber(resultSet.getString("personal_number"))
                        .build();
                employeeContactSet.add(employeeContact);
            }
            return employeeContactSet;
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
    public static EmployeeContactDao getInstance() {
        return INSTANCE;
    }
}
