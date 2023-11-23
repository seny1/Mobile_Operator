package org.elSasen.dao;

import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckDao {

    private static final CheckDao INSTANCE = new CheckDao();
    private final String DEFAULT_ORDER_BY = "check_id";

    public List<Check> getCheckTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT p.product_id,
                       p.price,
                       p.product_description,
                       p.product_name,
                       pc.category_id,
                       pc.name,
                       pc.description,
                       p.count,
                       product_count,
                       check_id,
                       c.client_id,
                       cp.passport_id,
                       cp.series,
                       cp.number,
                       cp.birthday,
                       c.first_name AS client_first_name,
                       c.last_name AS client_last_name,
                       cc.contact_id,
                       cc.number,
                       cc.type
                FROM "Check"
                JOIN public.product p on "Check".product_id = p.product_id
                JOIN public.client c on "Check".client_id = c.client_id
                JOIN public.product_category pc on p.category_id = pc.category_id
                JOIN public.client_passport cp on c.passport_id = cp.passport_id
                JOIN public.client_contact cc on c.contact_id = cc.contact_id
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var checkSet = new ArrayList<Check>();
            Check check;
            while (resultSet.next()) {
                check = Check.builder()
                        .product(Product.builder()
                                .productId(resultSet.getLong("product_id"))
                                .price(resultSet.getDouble("price"))
                                .productDescription(resultSet.getString("product_description"))
                                .productName(resultSet.getString("product_name"))
                                .category(ProductCategory.builder()
                                        .categoryId(resultSet.getInt("category_id"))
                                        .name(resultSet.getString("name"))
                                        .description(resultSet.getString("description"))
                                        .build())
                                .count(resultSet.getInt("count"))
                                .build())
                        .productCount(resultSet.getInt("product_count"))
                        .checkId(resultSet.getLong("check_id"))
                        .client(Client.builder()
                                .clientId(resultSet.getLong("client_id"))
                                .passport(ClientPassport.builder()
                                        .passportId(resultSet.getLong("passport_id"))
                                        .series(resultSet.getString("series"))
                                        .numberOfPassport(resultSet.getString("number"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .build())
                                .firstName(resultSet.getString("client_first_name"))
                                .lastName(resultSet.getString("client_last_name"))
                                .contact(ClientContact.builder()
                                        .contactId(resultSet.getLong("contact_id"))
                                        .numberOfContact(resultSet.getString("number"))
                                        .type(resultSet.getString("type"))
                                        .build())
                                .build())
                        .build();
                checkSet.add(check);
            }
            return checkSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM "Check";
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
                SELECT product_name,
                       product_count,
                       first_name AS client_first_name,
                       last_name AS client_last_name
                FROM "Check"
                JOIN public.product p on "Check".product_id = p.product_id
                JOIN public.client c on "Check".client_id = c.client_id
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

    public void insertIntoCheckTable(Map<String, Integer> productNameCount, int clientId) {
        String getProductId = """
                SELECT product_id
                FROM product
                WHERE product_name = ?;
                """;
        String insertIntoCheck = """
                INSERT INTO "Check" (product_id, product_count, check_id, client_id)
                VALUES (?, ?, ?, ?);
                """;
        int currentCheckId = getCurrentCheckId();
        try (var connection = ConnectionManager.get();
        var preparedStatementGetProd = connection.prepareStatement(getProductId);
        var preparedStatementInsertCheck = connection.prepareStatement(insertIntoCheck)) {
            var productNames = productNameCount.keySet().toArray();
            for (int i = 0; i < productNameCount.size(); i++) {
                preparedStatementGetProd.setString(1, (String) productNames[i]);
                var resultSet = preparedStatementGetProd.executeQuery();
                resultSet.next();
                int tempProductId = resultSet.getInt(1);

                preparedStatementInsertCheck.setInt(1, tempProductId);
                preparedStatementInsertCheck.setInt(2, productNameCount.get((String) productNames[i]));
                preparedStatementInsertCheck.setInt(3, currentCheckId);
                preparedStatementInsertCheck.setInt(4, clientId);
                preparedStatementInsertCheck.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getCurrentCheckId() {
        String sql = """
                SELECT MAX(check_id) AS current
                FROM "Check";
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("current") + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CheckDao getInstance() {
        return INSTANCE;
    }
}
