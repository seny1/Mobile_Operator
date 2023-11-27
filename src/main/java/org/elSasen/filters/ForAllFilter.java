package org.elSasen.filters;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ForAllFilter implements Filter {
    private final String LOGIN = "/login";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (req.getRequestURI().equals(LOGIN) || req.getSession().getAttribute("user") != null) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
}
