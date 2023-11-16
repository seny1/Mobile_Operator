package org.elSasen.dao;

import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckDao {

    private static final CheckDao INSTANCE = new CheckDao();

    public Set<Check> getCheckTable() {
        String sql = """
                SELECT *
                FROM "Check"
                JOIN public.product p on "Check".product_id = p.product_id
                JOIN public.client c on "Check".client_id = c.client_id
                JOIN public.product_category pc on p.category_id = pc.category_id
                JOIN public.client_passport cp on c.passport_id = cp.passport_id
                JOIN public.client_contact cc on c.contact_id = cc.contact_id;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var checkSet = new HashSet<Check>();
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
                                        .number(resultSet.getString("number"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .build())
                                .firstName(resultSet.getString("first_name"))
                                .lastName(resultSet.getString("last_name"))
                                .contact(ClientContact.builder()
                                        .contactId(resultSet.getLong("contact_id"))
                                        .number(resultSet.getString("number"))
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
    public static CheckDao getInstance() {
        return INSTANCE;
    }
}
