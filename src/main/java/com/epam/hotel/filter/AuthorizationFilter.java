package com.epam.hotel.filter;

import com.epam.hotel.access.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_LOGIN_REQUIRED;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        if (getAccess(session, request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_LOGIN_REQUIRED);
            response.sendRedirect(ERROR_JSP);
        }
    }

    @Override
    public void init(FilterConfig filterConfig)  {
    }

    @Override
    public void destroy() {
    }

    private boolean getAccess(HttpSession session, String requestURI) {

        Role role = (Role) session.getAttribute(ROLE);

        if (Role.CLIENT == role) {
            return Role.CLIENT.getAccessMap().get(requestURI);
        } else if (Role.GUEST == role) {
            return Role.GUEST.getAccessMap().get(requestURI);
        } else if (Role.ADMIN == role) {
            return Role.ADMIN.getAccessMap().get(requestURI);
        } else {
            session.setAttribute(ROLE, Role.GUEST);
            return Role.GUEST.getAccessMap().get(requestURI);
        }
    }
}