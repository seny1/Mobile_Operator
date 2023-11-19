package org.elSasen.dao;

import org.elSasen.entities.Product;
import org.elSasen.entities.ProductCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final ProductDao INSTANCE = new ProductDao();
    private final String DEFAULT_ORDER_BY = "product_id";
    public List<Product> getProductTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT product_id,
                price,
                product_description,
                product_name,
                product.category_id,
                count,
                product_category.category_id,
                product_category.name,
                product_category.description
                FROM product
                JOIN product_category on product.category_id = product_category.category_id
                ORDER BY product.
                """ + orderBy;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var productList = new ArrayList<Product>();
            Product product;
            while (resultSet.next()) {
                product = Product.builder()
                        .productId(resultSet.getInt("product_id"))
                        .price(resultSet.getDouble("price"))
                        .productDescription(resultSet.getString("product_description"))
                        .productName(resultSet.getString("product_name"))
                        .category(ProductCategory.builder()
                                .categoryId(resultSet.getInt("category_id"))
                                .name(resultSet.getString("name"))
                                .description(resultSet.getString("description"))
                                .build())
                        .count(resultSet.getInt("count"))
                        .build();
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT product_id,
                price,
                product_description,
                product_name,
                category_id,
                count
                FROM product
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

    public List<String> getProductNames() {
        String sql = """
                SELECT product_name
                FROM product;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var list = new ArrayList<String>();
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
