package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.entity.OrderFacilityDetail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.SHOW_PACKAGE_ADMIN_LIST_URL;

public class CreatePackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String packageName = request.getParameter("package_name");
        String facilities[] = request.getParameterValues("facilities");

        OrderFacilityDetailDAOImpl orderFacilityDetailDAO = new OrderFacilityDetailDAOImpl();
        long packageId = orderFacilityDetailDAO.create(new OrderFacilityDetail(packageName));

        for (String facilityId : facilities) {
            orderFacilityDetailDAO.createFacilityPackageRelation(Long.parseLong(facilityId), packageId);
        }
        request.getRequestDispatcher(SHOW_PACKAGE_ADMIN_LIST_URL).forward(request, response);
    }
}