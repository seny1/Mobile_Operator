package org.elSasen.dao;

import org.elSasen.entities.Product;
import org.elSasen.entities.ProductCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                product_category.category_id as id_of_category,
                product_category.name as name,
                product_category.description
                FROM product
                JOIN product_category on product.category_id = product_category.category_id
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var productList = new ArrayList<Product>();
            Product product;
            while (resultSet.next()) {
                product = productBuilder(resultSet);
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
    public List<String> getGoodMetaData() {
        String sql = """
                SELECT product_id,
                price,
                product_description,
                product_name,
                name as category_name,
                count
                FROM product
                JOIN product_category ON product.category_id = product_category.category_id
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

    public void insertIntoProductTable(Product product) {
        String getCategoryId = """
                SELECT category_id
                FROM product_category
                WHERE name = ?;
                """;
        String insertProduct = """
                INSERT INTO product (price, product_description, product_name, category_id, count)
                VALUES (?, ?, ?, ?, ?);
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatementCategoryId = connection.prepareStatement(getCategoryId);
        var preparedStatementInsertProduct = connection.prepareStatement(insertProduct)) {
            preparedStatementCategoryId.setString(1, product.getCategory().getName());
            var resultSet = preparedStatementCategoryId.executeQuery();
            resultSet.next();
            var tempCategoryId = resultSet.getInt(1);

            preparedStatementInsertProduct.setDouble(1, product.getPrice());
            preparedStatementInsertProduct.setString(2, product.getProductDescription());
            preparedStatementInsertProduct.setString(3, product.getProductName());
            preparedStatementInsertProduct.setInt(4, tempCategoryId);
            preparedStatementInsertProduct.setInt(5, product.getCount());
            preparedStatementInsertProduct.executeUpdate();
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

    public Optional<Product> getProductByName(String name) {
        String sql = """
                SELECT *
                FROM product
                JOIN product_category on product.category_id = product_category.category_id
                WHERE product_name = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(productBuilder(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Product productBuilder(ResultSet resultSet) throws SQLException {
        return Product.builder()
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
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
