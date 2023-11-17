package org.elSasen.dao;

import org.elSasen.entities.ProductCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductCategoryDao {
    private static final ProductCategoryDao INSTANCE = new ProductCategoryDao();
    private final String DEFAULT_ORDER_BY = "category_id";
    public List<ProductCategory> getProductCategoryTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT category_id,
                name,
                description
                FROM product_category
                ORDER BY 
                """ + orderBy;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var productCategoryList = new ArrayList<ProductCategory>();
            ProductCategory productCategory;
            while (resultSet.next()) {
                productCategory = ProductCategory.builder()
                        .categoryId(resultSet.getInt("category_id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .build();
                productCategoryList.add(productCategory);
            }
            return productCategoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT category_id,
                name,
                description
                FROM product_category
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

    public static ProductCategoryDao getInstance() {
        return INSTANCE;
    }
}
