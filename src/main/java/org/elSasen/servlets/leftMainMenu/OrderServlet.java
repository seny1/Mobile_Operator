package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.ClientDtoInsert;
import org.elSasen.dto.insert.OrderDtoInsert;
import org.elSasen.exception.ValidationException;
import org.elSasen.service.ClientService;
import org.elSasen.service.EmployeePassportService;
import org.elSasen.service.ExtraServiceService;
import org.elSasen.service.OrderService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

@WebServlet("/orderTable")
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = OrderService.getInstance();
    private final ClientService clientService = ClientService.getInstance();
    private final ExtraServiceService extraServiceService = ExtraServiceService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("orderTable", orderService.getOrderTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", orderService.getColumnsOfOrder());
        req.setAttribute("goodColumnNames", orderService.getGoodColumnsOfOrder());
        req.getSession().setAttribute("services", extraServiceService.getServices());
        req.getRequestDispatcher("leftMainMenu/OrderJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("filter") == null && req.getParameter("delete") == null) {
            if (req.getParameter("choose") == null && req.getParameter("ready") == null && req.getParameter("update") == null) {
                var clientDtoInsert = ClientDtoInsert.builder()
                    .firstName(req.getParameter("firstName"))
                    .lastName(req.getParameter("lastName"))
                    .series(req.getParameter("series"))
                    .numberOfPassport(req.getParameter("numberOfPassport"))
                    .birthday(LocalDate.parse(req.getParameter("birthday")))
                    .numberOfContact(req.getParameter("numberOfContact"))
                    .type(req.getParameter("type"))
                    .build();
                try {
                    var clientId = clientService.insertIntoClientTable(clientDtoInsert);
                    req.getSession().setAttribute("clientId", clientId);
                    resp.sendRedirect("/orderTable#win3");
                } catch (ValidationException exception) {
                    req.setAttribute("errors", exception.getErrors());
                    doGet(req, resp);
                }
            } else if (req.getParameter("choose") != null && req.getParameter("choose").equals("Да")) {
                resp.sendRedirect("/orderTable#win3");
            } else if (req.getParameter("choose") != null && req.getParameter("choose").equals("Нет")) {
                resp.sendRedirect("/orderTable#win2");
            } else if (req.getParameter("ready") != null && req.getParameter("ready").equals("ready")) {
                var orderDtoInsert = OrderDtoInsert.builder()
                    .serviceName(req.getParameter("serviceName"))
                    .employeeId(Integer.parseInt(req.getParameter("employeeId")))
                    .clientId(Integer.parseInt(req.getParameter("clientId")))
                    .model(req.getParameter("model"))
                    .clientProblem(req.getParameter("clientProblem"))
                    .comment(req.getParameter("comment"))
                    .build();
                try {
                    orderService.insertIntoOrder(orderDtoInsert);
                    req.getSession().removeAttribute("clientId");
                    resp.sendRedirect("/orderTable");
                } catch (ValidationException exception) {
                    req.setAttribute("errors", exception.getErrors());
                    doGet(req, resp);
                }
            }
            if (req.getParameter("update") != null) {
                int orderId = Integer.parseInt(req.getParameter("orderId"));
                String statusName = req.getParameter("statusName");
                orderService.updateStatus(orderId, statusName);
                doGet(req, resp);
            }
        } else if(req.getParameter("filter") != null) {
            var filterMap = new HashMap<String, String>();
            filterMap.put("serviceName", req.getParameter("serviceNameFilter"));
            filterMap.put("employeeFirstName", req.getParameter("employeeFirstNameFilter"));
            filterMap.put("employeeLastName", req.getParameter("employeeLastNameFilter"));
            filterMap.put("clientFirstName", req.getParameter("clientFirstNameFilter"));
            filterMap.put("clientLastName", req.getParameter("clientLastNameFilter"));
            filterMap.put("model", req.getParameter("modelFilter"));
            req.setAttribute("goodColumnNames", orderService.getGoodColumnsOfOrder());
            req.setAttribute("orderTable", orderService.getFilterOrderTable(req.getParameter("orderBy"), filterMap));
            req.getRequestDispatcher("leftMainMenu/OrderJSP.jsp").forward(req, resp);
        } else if (req.getParameter("delete") != null) {
            orderService.deleteOrder(Integer.parseInt(req.getParameter("orderIdDelete")));
            resp.sendRedirect("/orderTable?good=good");
        }
    }
}
