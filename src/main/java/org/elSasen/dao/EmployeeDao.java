package org.elSasen.dao;


import org.elSasen.entities.Employee;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.Optional;

public class EmployeeDao {

    private static final EmployeeDao INSTANCE = new EmployeeDao();

    public Optional<Employee> findByLoginAndPassword(String login, String password) {
        String sql = """
                SELECT *
                FROM employee
                WHERE login = ? AND password = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            Employee employee = null;
            if (resultSet.next()) {
                employee = Employee.builder()
                        .employeeId(resultSet.getLong("employee_id"))
                        .departmentId(resultSet.getLong("department_id"))
                        .salonId(resultSet.getLong("salon_id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .postId(resultSet.getLong("post_id"))
                        .passportId(resultSet.getLong("passport_id"))
                        .contactId(resultSet.getInt("contact_id"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .roleId(resultSet.getLong("role_id"))
                        .build();
            }
            return Optional.ofNullable(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static EmployeeDao getInstance() {
        return INSTANCE;
    }
}
