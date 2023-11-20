package org.elSasen.dao;

import org.elSasen.entities.CommunicationSalon;
import org.elSasen.entities.Department;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {

    private static final DepartmentDao INSTANCE = new DepartmentDao();
    private final String DEFAULT_ORDER_BY = "department_id";

    public List<Department> getDepartmentTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT department_id,
                       department_name,
                       start_time,
                       end_time
                FROM department
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            Department department;
            var departmentSet = new ArrayList<Department>();
            while (resultSet.next()) {
                department = Department.builder()
                        .departmentId(resultSet.getLong("department_id"))
                        .departmentName(resultSet.getString("department_name"))
                        .startTime(resultSet.getObject("start_time", LocalTime.class))
                        .endTime(resultSet.getObject("end_time", LocalTime.class))
                        .build();
                departmentSet.add(department);
            }
            return departmentSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM department;
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

    public int getDepartmentIdByDepartmentName(String departmentName) {
        String sql = """
                SELECT department_id
                FROM department
                WHERE department_name = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, departmentName);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDepartments() {
        String sql = """
                SELECT department_name
                FROM department;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var departments = new ArrayList<String>();
            while (resultSet.next()) {
                departments.add(resultSet.getString(1));
            }
            return departments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static DepartmentDao getInstance() {
        return INSTANCE;
    }
}
