package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ServiceCategoryService;
import org.elSasen.service.StatusService;

import java.io.IOException;

@WebServlet("/statusTable")
public class StatusServlet extends HttpServlet {
    private final StatusService statusService = StatusService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("statusTable", statusService.getStatusTable());
        req.setAttribute("columnNames", statusService.getColumnsOfStatus());
        req.getRequestDispatcher("leftMainMenu/StatusJSP.jsp").forward(req, resp);
    }
}
