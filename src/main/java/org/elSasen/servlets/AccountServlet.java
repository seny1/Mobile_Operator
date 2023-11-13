package org.elSasen.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.EmployeeService;

import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private final EmployeeService employeeService = EmployeeService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("AccountJSP.jsp").forward(req, resp);
    }
}
