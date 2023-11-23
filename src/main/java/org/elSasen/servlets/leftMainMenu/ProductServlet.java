package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.ProductDtoInsert;
import org.elSasen.exception.ValidationException;
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
        req.setAttribute("goodColumnNames", productService.getGoodColumnsOfProduct());
        req.getRequestDispatcher("leftMainMenu/ProductJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var productDtoInsert = ProductDtoInsert.builder()
                .price(Double.parseDouble(req.getParameter("price")))
                .productDescription(req.getParameter("productDescription"))
                .productName(req.getParameter("productName"))
                .categoryName(req.getParameter("categoryName"))
                .count(Integer.parseInt(req.getParameter("productCount")))
                .build();
        try {
            productService.insertIntoProductTable(productDtoInsert);
            resp.sendRedirect("/productTable");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
