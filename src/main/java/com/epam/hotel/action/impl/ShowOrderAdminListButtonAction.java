package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.SHOW_ORDER_ADMIN_LIST_JSP;

public class ShowOrderAdminListButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(SHOW_ORDER_ADMIN_LIST_JSP);
    }
}
