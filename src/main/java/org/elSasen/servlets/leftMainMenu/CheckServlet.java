package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.CheckDtoInsert;
import org.elSasen.exception.ValidationException;
import org.elSasen.service.CheckService;
import org.elSasen.service.ProductService;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/checkTable")
public class CheckServlet extends HttpServlet {

    private final CheckService checkService = CheckService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("checkTable", checkService.getCheckTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", checkService.getColumnsOfCheck());
        req.setAttribute("goodColumnNames", checkService.getGoodMetaData());
        req.getRequestDispatcher("leftMainMenu/CheckJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("numberOfTypes") != null) {
            req.getSession().setAttribute("products", productService.getProductNames());
            resp.sendRedirect("/checkTable?numberOfTypes=" + req.getParameter("numberOfTypes") + "#win2");
        } else {
            try {
                if (req.getParameter("filter") == null && req.getParameter("delete") == null) {
                    String[] productNames = new String[Integer.parseInt(req.getParameter("i"))];
                    Integer[] productCounts = new Integer[Integer.parseInt(req.getParameter("i"))];

                    for (int i = 1; i < productNames.length + 1; i++) {
                        productNames[i - 1] = req.getParameter("productName" + i);
                        productCounts[i - 1] = Integer.parseInt(req.getParameter("productCount" + i));
                    }
                    var checkDto = CheckDtoInsert.builder()
                            .productName(productNames)
                            .productCount(productCounts)
                            .clientId(Integer.parseInt(req.getParameter("clientID")))
                            .build();
                    checkService.insertIntoCheckTable(checkDto);
                    resp.sendRedirect("/checkTable");
                } else if (req.getParameter("filter") != null) {
                    var filterMap = new HashMap<String, String>();
                    filterMap.put("productName", req.getParameter("productNameFilter"));
                    filterMap.put("productCountUp", req.getParameter("productCountUpFilter"));
                    filterMap.put("productCountDown", req.getParameter("productCountDownFilter"));
                    filterMap.put("firstName", req.getParameter("firstNameFilter"));
                    filterMap.put("lastName", req.getParameter("lastNameFilter"));
                    req.setAttribute("goodColumnNames", checkService.getGoodMetaData());
                    req.setAttribute("checkTable", checkService.getFilterCallTable(req.getParameter("orderBy"), filterMap));
                    req.getRequestDispatcher("leftMainMenu/CheckJSP.jsp").forward(req, resp);
                } else if (req.getParameter("delete") != null) {
                    checkService.deleteCheck(Integer.parseInt(req.getParameter("checkIdDelete")));
                    resp.sendRedirect("/checkTable?good=good");
                }
            } catch (ValidationException exception) {
                req.setAttribute("errors", exception.getErrors());
                doGet(req, resp);
            }
        }
    }
}
