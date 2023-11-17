package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.PostService;
import org.elSasen.service.ProductService;

import java.io.IOException;

@WebServlet("/productTable")
public class ProductServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productTable", productService.getProductTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", productService.getColumnsOfProduct());
        req.getRequestDispatcher("leftMainMenu/ProductJSP.jsp").forward(req, resp);
    }
}
