package org.elSasen.dao;

import org.elSasen.entities.EmployeePassport;
import org.elSasen.entities.ExtraService;
import org.elSasen.entities.ServiceCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtraServiceDao {
    private static final ExtraServiceDao INSTANCE = new ExtraServiceDao();
    public Set<ExtraService> getExtraServiceTable() {
        String sql = """
                SELECT *
                FROM extra_service
                JOIN service_category on extra_service.category_id = service_category.category_id;
                """;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var extraServiceSet = new HashSet<ExtraService>();
            ExtraService extraService;
            while (resultSet.next()) {
                extraService = ExtraService.builder()
                        .serviceId(resultSet.getLong("service_id"))
                        .serviceDescription(resultSet.getString("service_description"))
                        .price(resultSet.getDouble("price"))
                        .serviceName(resultSet.getString("service_name"))
                        .category(ServiceCategory.builder()
                                .categoryId(resultSet.getInt("category_id"))
                                .name(resultSet.getString("name"))
                                .difficulty(resultSet.getString("difficulty"))
                                .description(resultSet.getString("description"))
                                .build())
                        .build();
                extraServiceSet.add(extraService);
            }
            return extraServiceSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM extra_service
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

    public static ExtraServiceDao getInstance() {
        return INSTANCE;
    }
}
