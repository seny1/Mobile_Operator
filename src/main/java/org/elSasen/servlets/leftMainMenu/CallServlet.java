package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.CallDtoInsert;
import org.elSasen.exception.ValidationException;
import org.elSasen.service.CallService;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/callTable")
public class CallServlet extends HttpServlet {

    private final CallService callService = CallService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("callTable", callService.getCallTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", callService.getColumnsOfCall());
        req.setAttribute("goodColumnNames", callService.getGoodColumnsOfCall());
        req.getRequestDispatcher("leftMainMenu/CallJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("filter") == null) {
                var callDtoInsert = CallDtoInsert.builder()
                        .clientId(Integer.parseInt(req.getParameter("clientID")))
                        .subscriberNumber(req.getParameter("subscriberNumber"))
                        .callDuration(Double.parseDouble(req.getParameter("callDuration")))
                        .build();
                callService.insertIntoCallTable(callDtoInsert);
                resp.sendRedirect("/callTable");
            } else if (req.getParameter("filter") != null) {
                var filterMap = new HashMap<String, String>();
                filterMap.put("subscriberNumber", req.getParameter("subscriberNumberFilter"));
                filterMap.put("callDurationUp", req.getParameter("callDurationUpFilter"));
                filterMap.put("callDurationDown", req.getParameter("callDurationDownFilter"));
                filterMap.put("firstName", req.getParameter("firstNameFilter"));
                filterMap.put("lastName", req.getParameter("lastNameFilter"));
                req.setAttribute("goodColumnNames", callService.getGoodColumnsOfCall());
                req.setAttribute("callTable", callService.getFilterCallTable(req.getParameter("orderBy"), filterMap));
                req.getRequestDispatcher("leftMainMenu/CallJSP.jsp").forward(req, resp);
            }
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
