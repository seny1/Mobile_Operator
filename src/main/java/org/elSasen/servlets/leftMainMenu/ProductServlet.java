package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.PostService;
import org.elSasen.service.ProductCategoryService;
import org.elSasen.service.ProductService;

import java.io.IOException;

@WebServlet("/productTable")
public class ProductServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();

    private final ProductCategoryService productCategoryService = ProductCategoryService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productTable", productService.getProductTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", productService.getColumnsOfProduct());
        req.getSession().setAttribute("categories", productCategoryService.getCategories());
        req.getRequestDispatcher("leftMainMenu/ProductJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productService.insertIntoProductTable(
                Double.parseDouble(req.getParameter("price")),
                req.getParameter("productDescription"),
                req.getParameter("productName"),
                req.getParameter("categoryName"),
                Integer.parseInt(req.getParameter("productCount"))
        );
        resp.sendRedirect("/productTable");
    }
}
