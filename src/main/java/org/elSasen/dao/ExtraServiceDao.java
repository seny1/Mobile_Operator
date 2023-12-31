package org.elSasen.dao;

import org.elSasen.entities.ExtraService;
import org.elSasen.entities.ServiceCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExtraServiceDao {
    private static final ExtraServiceDao INSTANCE = new ExtraServiceDao();
    private final String DEFAULT_ORDER_BY = "service_id";
    private final ServiceCategoryDao serviceCategoryDao = ServiceCategoryDao.getInstance();
    public List<ExtraService> getExtraServiceTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT service_id,
                service_description,
                price,
                service_name,
                service_category.category_id,
                service_category.name as category_name,
                service_category.difficulty as category_difficulty,
                service_category.description
                FROM extra_service
                JOIN service_category on extra_service.category_id = service_category.category_id
                order by
                """ + orderBy;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var extraServiceList = new ArrayList<ExtraService>();
            ExtraService extraService;
            while (resultSet.next()) {
                extraService = ExtraService.builder()
                        .serviceId(resultSet.getLong("service_id"))
                        .serviceDescription(resultSet.getString("service_description"))
                        .price(resultSet.getDouble("price"))
                        .serviceName(resultSet.getString("service_name"))
                        .category(ServiceCategory.builder()
                                .categoryId(resultSet.getInt("category_id"))
                                .name(resultSet.getString("category_name"))
                                .difficulty(resultSet.getString("category_difficulty"))
                                .description(resultSet.getString("description"))
                                .build())
                        .build();
                extraServiceList.add(extraService);
            }
            return extraServiceList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT service_id,
                service_description,
                price,
                service_name,
                category_id
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
    public List<String> getGoodMetaData() {
        String sql = """
                SELECT service_id,
                service_description,
                price,
                service_name,
                service_category.name as category_name,
                service_category.difficulty as category_difficulty
                FROM extra_service
                JOIN service_category on extra_service.category_id = service_category.category_id
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

    public int getServiceIdByName(String serviceName) {
        String sql = """
                SELECT service_id
                FROM extra_service
                WHERE service_name = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, serviceName);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteService(String name){
        String sql = """
            DELETE FROM extra_service
            WHERE service_name = ?
            """;
        var serviceName = getServiceName(name);
        if(serviceName.isEmpty()){
            return false;
        }
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, serviceName.get());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getServiceName(String name){
        String sql = """
            SELECT service_name FROM extra_service
            WHERE service_name = ?
            """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(resultSet.getString("service_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getServices() {
        String sql = """
                SELECT service_name
                FROM extra_service;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var services = new ArrayList<String>();
            while (resultSet.next()) {
                services.add(resultSet.getString(1));
            }
            return services;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertIntoService(ExtraService extraService) {
        String sql = """
                INSERT INTO extra_service (service_description, price, service_name, category_id)
                VALUES (?, ?, ?, ?);
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var tempCategoryId = serviceCategoryDao.getCategoryIdByName(extraService.getCategory().getName());
            preparedStatement.setString(1, extraService.getServiceDescription());
            preparedStatement.setDouble(2, extraService.getPrice());
            preparedStatement.setString(3, extraService.getServiceName());
            preparedStatement.setInt(4, tempCategoryId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ExtraServiceDao getInstance() {
        return INSTANCE;
    }
}
