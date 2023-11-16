package org.elSasen.dao;

import org.elSasen.entities.Post;
import org.elSasen.entities.Product;
import org.elSasen.entities.ProductCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDao {
    private static final ProductDao INSTANCE = new ProductDao();
    public Set<Product> getProductTable() {
        String sql = """
                SELECT *
                FROM product
                JOIN product_category on product.category_id = product_category.category_id
                """;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var productSet = new HashSet<Product>();
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
                productSet.add(product);
            }
            return productSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT *
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

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
