package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.RoleService;

import java.io.IOException;

@WebServlet("/roleTable")
public class RoleServlet extends HttpServlet {
    private final RoleService roleService = RoleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roleTable", roleService.getRoleTable());
        req.setAttribute("columnNames", roleService.getColumnsOfRole());
        req.getRequestDispatcher("leftMainMenu/RoleJSP.jsp").forward(req, resp);
    }
}
