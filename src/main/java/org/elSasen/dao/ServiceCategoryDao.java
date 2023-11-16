package org.elSasen.dao;

import org.elSasen.entities.ServiceCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceCategoryDao {
    private static final ServiceCategoryDao INSTANCE = new ServiceCategoryDao();
    public Set<ServiceCategory> getServiceCategoryTable() {
        String sql = """
                SELECT *
                FROM service_category
                """;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var serviceCategorySet = new HashSet<ServiceCategory>();
            ServiceCategory serviceCategory;
            while (resultSet.next()) {
                serviceCategory = ServiceCategory.builder()
                        .categoryId(resultSet.getInt("category_id"))
                        .name(resultSet.getString("name"))
                        .difficulty(resultSet.getString("difficulty"))
                        .description(resultSet.getString("description"))
                        .build();
                serviceCategorySet.add(serviceCategory);
            }
            return serviceCategorySet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM service_category
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

    public static ServiceCategoryDao getInstance() {
        return INSTANCE;
    }
}
