package com.epam.hotel.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageFilter implements Filter {

    public static final String LOCAL = "locale";
    public static final String LANGUAGE = "language";
    public static final String EN = "en_US";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpSession session = request.getSession();

        if (request.getParameter(LANGUAGE) != null) {
            session.setAttribute(LOCAL, request.getParameter(LANGUAGE));
        } else if (session.getAttribute(LOCAL) != null) {
            String local = (String) session.getAttribute(LOCAL);
            session.setAttribute(LOCAL, local);
        } else {
            session.setAttribute(LOCAL, EN);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}