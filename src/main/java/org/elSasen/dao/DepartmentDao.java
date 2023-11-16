package org.elSasen.dao;

import org.elSasen.entities.Department;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartmentDao {

    private static final DepartmentDao INSTANCE = new DepartmentDao();

    public Set<Department> getDepartmentTable() {
        String sql = """
                SELECT *
                FROM department;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            Department department;
            var departmentSet = new HashSet<Department>();
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

    public static DepartmentDao getInstance() {
        return INSTANCE;
    }
}
