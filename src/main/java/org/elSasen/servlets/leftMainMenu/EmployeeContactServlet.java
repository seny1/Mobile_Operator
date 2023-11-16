package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.EmployeeContactService;

import java.io.IOException;

@WebServlet("/employeeContactTable")
public class EmployeeContactServlet extends HttpServlet {

    private final EmployeeContactService employeeContactService = EmployeeContactService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employeeContactTable", employeeContactService.getEmployeeContactTable());
        req.setAttribute("columnsNames", employeeContactService.getColumnsOfEmployeeContact());
        req.getRequestDispatcher("leftMainMenu/EmployeeContactJSP.jsp").forward(req, resp);
    }
}
