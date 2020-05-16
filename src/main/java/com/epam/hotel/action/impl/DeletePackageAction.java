package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.SHOW_PACKAGE_ADMIN_LIST_URL;

public class DeletePackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long packageId = Long.parseLong(request.getParameter("package_id"));

        OrderFacilityDetailDAOImpl orderFacilityDetailDAO = new OrderFacilityDetailDAOImpl();
        orderFacilityDetailDAO.deleteOneById(packageId);

        request.getRequestDispatcher(SHOW_PACKAGE_ADMIN_LIST_URL).forward(request, response);
    }
}
