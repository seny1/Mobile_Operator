package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.EmployeePassportService;
import org.elSasen.service.OrderService;

import java.io.IOException;

@WebServlet("/orderTable")
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("orderTable", orderService.getOrderTable());
        req.setAttribute("columnNames", orderService.getColumnsOfOrder());
        req.getRequestDispatcher("leftMainMenu/OrderJSP.jsp").forward(req, resp);
    }
}
