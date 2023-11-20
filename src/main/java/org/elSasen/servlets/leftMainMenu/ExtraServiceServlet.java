package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ExtraServiceService;
import org.elSasen.service.ServiceCategoryService;

import java.io.IOException;

@WebServlet("/extraServiceTable")
public class ExtraServiceServlet extends HttpServlet {
    private final ExtraServiceService extraServiceService = ExtraServiceService.getInstance();
    private final ServiceCategoryService serviceCategoryService = ServiceCategoryService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("extraServiceTable", extraServiceService.getExtraServiceTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", extraServiceService.getColumnsOfExtraService());
        req.getSession().setAttribute("serviceCategories", serviceCategoryService.getServiceCategories());
        req.getRequestDispatcher("leftMainMenu/ExtraServiceJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        extraServiceService.insertIntoService(
                req.getParameter("description"),
                Double.parseDouble(req.getParameter("price")),
                req.getParameter("serviceName"),
                req.getParameter("serviceCategory")
        );
        resp.sendRedirect("/extraServiceTable");
    }
}