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
import java.util.HashMap;

@WebServlet("/extraServiceTable")
public class ExtraServiceServlet extends HttpServlet {
    private final ExtraServiceService extraServiceService = ExtraServiceService.getInstance();
    private final ServiceCategoryService serviceCategoryService = ServiceCategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("extraServiceTable", extraServiceService.getExtraServiceTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", extraServiceService.getColumnsOfExtraService());
        req.setAttribute("goodColumnNames", extraServiceService.getGoodColumnsOfExtraService());
        req.getSession().setAttribute("serviceCategories", extraServiceService.getServices());
        req.getSession().setAttribute("names", extraServiceService.getServices());
        req.getRequestDispatcher("leftMainMenu/ExtraServiceJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("filter") == null && req.getParameter("delete") == null) {
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
            } else if(req.getParameter("filter") != null) {
                var filterMap = new HashMap<String, String>();
                filterMap.put("serviceName", req.getParameter("serviceNameFilter"));
                filterMap.put("priceUp", req.getParameter("priceUpFilter"));
                filterMap.put("priceDown", req.getParameter("priceDownFilter"));
                filterMap.put("categoryName", req.getParameter("categoryNameFilter"));
                req.setAttribute("goodColumnNames", extraServiceService.getGoodColumnsOfExtraService());
                req.setAttribute("extraServiceTable", extraServiceService.getFilterExtraServiceTable(req.getParameter("orderBy"), filterMap));
                req.getRequestDispatcher("leftMainMenu/ExtraServiceJSP.jsp").forward(req, resp);
            } else if (req.getParameter("delete") != null){
                extraServiceService.deleteService(req.getParameter("nameDelete"));
                resp.sendRedirect("/extraServiceTable?good=good");
            }
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}