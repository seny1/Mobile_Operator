package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ClientService;
import org.elSasen.service.EmployeePassportService;
import org.elSasen.service.ExtraServiceService;
import org.elSasen.service.OrderService;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/orderTable")
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = OrderService.getInstance();
    private final ClientService clientService = ClientService.getInstance();
    private final ExtraServiceService extraServiceService = ExtraServiceService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("orderTable", orderService.getOrderTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", orderService.getColumnsOfOrder());
        req.getSession().setAttribute("services", extraServiceService.getServices());
        req.getRequestDispatcher("leftMainMenu/OrderJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("choose") == null && req.getParameter("ready") == null) {
            var clientId = clientService.insertIntoClientTable(
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    req.getParameter("series"),
                    req.getParameter("numberOfPassport"),
                    LocalDate.parse(req.getParameter("birthday")),
                    req.getParameter("numberOfContact"),
                    req.getParameter("type")
            );
            req.getSession().setAttribute("clientId", clientId);
            resp.sendRedirect("/orderTable#win3");
        } else if (req.getParameter("choose") != null && req.getParameter("choose").equals("Да")) {
            resp.sendRedirect("/orderTable#win3");
        } else if (req.getParameter("choose") != null && req.getParameter("choose").equals("Нет")) {
            resp.sendRedirect("/orderTable#win2");
        } else if (req.getParameter("ready").equals("ready")) {
            orderService.insertIntoOrder(
                    req.getParameter("serviceName"),
                    Integer.parseInt(req.getParameter("employeeId")),
                    Integer.parseInt(req.getParameter("clientId")),
                    req.getParameter("model"),
                    req.getParameter("clientProblem"),
                    req.getParameter("comment")
            );
            req.getSession().removeAttribute("clientId");
            resp.sendRedirect("/orderTable");
        }
    }
}
