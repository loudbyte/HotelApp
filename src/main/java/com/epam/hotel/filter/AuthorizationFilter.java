package com.epam.hotel.filter;


import com.epam.hotel.entity.Person;
import com.epam.hotel.role.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");

        if (role != null) {
            filterChain.doFilter(request, response);
        } else {
            session.setAttribute("role", Role.GUEST);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
