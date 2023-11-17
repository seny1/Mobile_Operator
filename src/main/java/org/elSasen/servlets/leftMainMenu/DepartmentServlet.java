package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.DepartmentService;

import java.io.IOException;

@WebServlet("/departmentTable")
public class DepartmentServlet extends HttpServlet {

    private final DepartmentService departmentService = DepartmentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("departmentTable", departmentService.getDepartmentTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", departmentService.getColumnsNamesOfDepartment());
        req.getRequestDispatcher("leftMainMenu/DepartmentJSP.jsp").forward(req, resp);
    }
}
