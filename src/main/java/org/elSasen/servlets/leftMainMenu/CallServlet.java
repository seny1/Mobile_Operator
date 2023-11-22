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

@WebServlet("/callTable")
public class CallServlet extends HttpServlet {

    private final CallService callService = CallService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("callTable", callService.getCallTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", callService.getColumnsOfCall());
        req.getRequestDispatcher("leftMainMenu/CallJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var callDtoInsert = CallDtoInsert.builder()
                .clientId(Integer.parseInt(req.getParameter("clientID")))
                .subscriberNumber(req.getParameter("subscriberNumber"))
                .callDuration(Double.parseDouble(req.getParameter("callDuration")))
                .build();
        try {
            callService.insertIntoCallTable(callDtoInsert);
            resp.sendRedirect("/callTable");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
