package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.CheckService;
import org.elSasen.service.ProductService;

import java.io.IOException;

@WebServlet("/checkTable")
public class CheckServlet extends HttpServlet {

    private final CheckService checkService = CheckService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("checkTable", checkService.getCheckTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", checkService.getColumnsOfCheck());
        req.getRequestDispatcher("leftMainMenu/CheckJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("numberOfTypes") != null) {
            req.getSession().setAttribute("products", productService.getProductNames());
            resp.sendRedirect("/checkTable?numberOfTypes=" + req.getParameter("numberOfTypes") + "#win2");
        } else {
            String[] productNames = new String[Integer.parseInt(req.getParameter("i"))];
            Integer[] productCounts = new Integer[Integer.parseInt(req.getParameter("i"))];

            for (int i = 1; i < productNames.length + 1; i++) {
                productNames[i - 1] = req.getParameter("productName" + i);
                productCounts[i - 1] = Integer.parseInt(req.getParameter("productCount" + i));
            }
            checkService.insertIntoCheckTable(productNames, productCounts, Integer.parseInt(req.getParameter("clientID")));
            resp.sendRedirect("/checkTable");
        }
    }
}
