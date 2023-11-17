package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ServiceCategoryService;

import java.io.IOException;

@WebServlet("/serviceCategoryTable")
public class ServiceCategoryServlet extends HttpServlet {
    private final ServiceCategoryService serviceCategoryService = ServiceCategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("serviceCategoryTable", serviceCategoryService.getServiceCategoryTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", serviceCategoryService.getColumnsOfServiceCategory());
        req.getRequestDispatcher("leftMainMenu/ServiceCategoryJSP.jsp").forward(req, resp);
    }
}
