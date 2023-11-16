package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.PostService;

import java.io.IOException;

@WebServlet("/postTable")
public class PostServlet extends HttpServlet {
    private final PostService postService = PostService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("postTable", postService.getPostTable());
        req.setAttribute("columnNames", postService.getColumnsOfPost());
        req.getRequestDispatcher("leftMainMenu/PostJSP.jsp").forward(req, resp);
    }
}
