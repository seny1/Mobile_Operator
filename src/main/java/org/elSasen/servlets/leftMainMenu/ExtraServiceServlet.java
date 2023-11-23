package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.ExtraServiceDtoInsert;
import org.elSasen.exception.ValidationException;
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
        req.setAttribute("goodColumnNames", extraServiceService.getGoodColumnsOfExtraService());
        req.getRequestDispatcher("leftMainMenu/ExtraServiceJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var extraServiceDtoInsert = ExtraServiceDtoInsert.builder()
                .serviceDescription(req.getParameter("description"))
                .serviceName(req.getParameter("serviceName"))
                .price(Double.parseDouble(req.getParameter("price")))
                .categoryName(req.getParameter("serviceCategory"))
                .build();
        try {
            extraServiceService.insertIntoService(extraServiceDtoInsert);
            resp.sendRedirect("/extraServiceTable");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}