package org.elSasen.dao;

import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderDao {
    private static final OrderDao INSTANCE = new OrderDao();
    private final String DEFAULT_ORDER_BY = "order_id";
    private final ClientDeviceDao clientDeviceDao = ClientDeviceDao.getInstance();
    private final ExtraServiceDao extraServiceDao = ExtraServiceDao.getInstance();
    public List<Order> getOrderTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT "Order".service_id,
                "Order".employee_id,
                "Order".client_id,
                "Order".order_id,
                "Order".status_id,
                "Order".device_id,
                "Order".comment,
                es.service_id,
                es.service_description,
                es.price,
                es.service_name,
                es.category_id,
                e.employee_id,
                e.department_id,
                e.salon_id,
                e.first_name,
                e.last_name,
                e.post_id,
                e.passport_id,
                e.contact_id,
                e.login,
                e.role_id,
                e.password,
                c.client_id,
                c.passport_id,
                c.first_name,
                c.last_name,
                c.contact_id,
                c.remain_minutes,
                s.status_id,
                s.name,
                s.description,
                cd.device_id,
                cd.model,
                cd.client_problem,
                sc.category_id,
                sc.name,
                sc.difficulty,
                sc.description,
                d.department_id,
                d.department_name,
                d.start_time,
                d.end_time,
                cs.salon_id,
                cs.address,
                cs.employee_number,
                p.post_id,
                p.post_name,
                p.post_description,
                ep.passport_id,
                ep.series,
                ep.number,
                ep.birthday,
                ep.issue_date,
                ep.place_code,
                ec.contact_id,
                ec.work_number,
                ec.personal_number,
                r.role_id,
                r.role_name,
                r.description,
                cc.contact_id,
                cc.number,
                cc.type
                FROM "Order"
                JOIN extra_service as es on "Order".service_id = es.service_id
                join employee as e on "Order".employee_id = e.employee_id
                join client as c on "Order".client_id = c.client_id
                join status as s on "Order".status_id = s.status_id
                join client_device as cd on "Order".device_id = cd.device_id
                join service_category as sc on es.category_id = sc.category_id
                join department as d on e.department_id = d.department_id
                join communication_salon as cs on e.salon_id = cs.salon_id
                join post as p on e.post_id = p.post_id
                join employee_passport as ep on e.passport_id = ep.passport_id
                join employee_contact as ec on e.contact_id = ec.contact_id
                join role as r on e.role_id = r.role_id
                join client_contact as cc on c.contact_id = cc.contact_id
                ORDER BY "Order".
                """ + orderBy;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var orderList = new ArrayList<Order>();
            Order order;
            while (resultSet.next()) {
                order = Order.builder()
                        .service(ExtraService.builder()
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
                                .build())
                        .employee(Employee.builder()
                                .employeeId(resultSet.getLong("employee_id"))
                                .department(Department.builder()
                                        .departmentId(resultSet.getLong("department_id"))
                                        .departmentName(resultSet.getString("department_name"))
                                        .startTime(resultSet.getObject("start_time", LocalTime.class))
                                        .endTime(resultSet.getObject("end_time", LocalTime.class))
                                        .build())
                                .salon(CommunicationSalon.builder()
                                        .salonId(resultSet.getLong("salon_id"))
                                        .address(resultSet.getString("address"))
                                        .employeeNumber(resultSet.getInt("employee_number"))
                                        .build())
                                .firstName(resultSet.getString("first_name"))
                                .lastName(resultSet.getString("last_name"))
                                .post(Post.builder()
                                        .postId(resultSet.getInt("post_id"))
                                        .postName(resultSet.getString("post_name"))
                                        .postDescription(resultSet.getString("post_description"))
                                        .build())
                                .passport(EmployeePassport.builder()
                                        .passportId(resultSet.getLong("passport_id"))
                                        .series(resultSet.getString("series"))
                                        .number(resultSet.getString("number"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .issueDate(resultSet.getObject("issue_date", LocalDate.class))
                                        .placeCode(resultSet.getString("place_code"))
                                        .build())
                                .contact(EmployeeContact.builder()
                                        .contactId(resultSet.getInt("contact_id"))
                                        .workNumber(resultSet.getString("work_number"))
                                        .personalNumber(resultSet.getString("personal_number"))
                                        .build())
                                .login(resultSet.getString("login"))
                                .password(resultSet.getString("password"))
                                .role(Role.builder()
                                        .roleId(resultSet.getLong("role_id"))
                                        .roleName(resultSet.getString("role_name"))
                                        .description(resultSet.getString("description"))
                                        .build())
                                .build())
                        .client(Client.builder()
                                .clientId(resultSet.getLong("client_id"))
                                .passport(ClientPassport.builder()
                                        .passportId(resultSet.getLong("passport_id"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .number(resultSet.getString("number"))
                                        .series(resultSet.getString("series"))
                                        .build())
                                .firstName(resultSet.getString("first_name"))
                                .lastName(resultSet.getString("last_name"))
                                .contact(ClientContact.builder()
                                        .contactId(resultSet.getLong("contact_id"))
                                        .number(resultSet.getString("number"))
                                        .type(resultSet.getString("type"))
                                        .build())
                                .build())
                        .orderId(resultSet.getInt("order_id"))
                        .status(Status.builder()
                                .statusId(resultSet.getInt("status_id"))
                                .name(resultSet.getString("name"))
                                .description(resultSet.getString("description"))
                                .build())
                        .device(ClientDevice.builder()
                                .deviceId(resultSet.getInt("device_id"))
                                .model(resultSet.getString("model"))
                                .clientProblem(resultSet.getString("client_problem"))
                                .build())
                        .comment(resultSet.getString("comment"))
                        .build();
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT "Order".service_id,
                "Order".employee_id,
                "Order".client_id,
                "Order".order_id,
                "Order".status_id,
                "Order".device_id,
                "Order".comment
                FROM "Order"
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

    public void insertIntoOrder(String serviceName, int employeeId, int clientId, String model, String clientProblem, String comment) {
        String sql = """
                INSERT INTO "Order" (service_id, employee_id, client_id, status_id, device_id, comment)
                VALUES (?, ?, ?, 3, ?, ?);
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var tempServiceId = extraServiceDao.getServiceIdByName(serviceName);
            var tempDeviceId = clientDeviceDao.insertDeviceAndReturnId(model, clientProblem);

            preparedStatement.setInt(1, tempServiceId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setInt(3, clientId);
            preparedStatement.setInt(4, tempDeviceId);
            preparedStatement.setString(5, comment);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
