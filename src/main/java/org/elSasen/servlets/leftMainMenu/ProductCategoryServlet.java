package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ProductCategoryService;
import org.elSasen.service.ProductService;

import java.io.IOException;

@WebServlet("/productCategoryTable")
public class ProductCategoryServlet extends HttpServlet {
    private final ProductCategoryService productCategoryService = ProductCategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productCategoryTable", productCategoryService.getProductCategoryTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", productCategoryService.getColumnsOfProductCategory());
        req.getRequestDispatcher("leftMainMenu/ProductCategoryJSP.jsp").forward(req, resp);
    }
}
