package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl;
import com.epam.hotel.entity.OrderFacilityDetail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.SHOW_PACKAGE_ADMIN_LIST_URL;

public class EditPackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long packageId = Long.parseLong(request.getParameter("package_id"));
        String newPackageName = request.getParameter("new_package_name");
        String facilities[] = request.getParameterValues("facilities");

        OrderFacilityDetailDAOImpl orderFacilityDetailDAO = new OrderFacilityDetailDAOImpl();
        orderFacilityDetailDAO.deleteFacilityPackageRelationByPackageId(packageId);

        for (String facilityId : facilities) {
            orderFacilityDetailDAO.createFacilityPackageRelation(Long.parseLong(facilityId), packageId);
        }

        orderFacilityDetailDAO.updateOneById(packageId, new OrderFacilityDetail(packageId, newPackageName));

        request.getRequestDispatcher(SHOW_PACKAGE_ADMIN_LIST_URL).forward(request, response);
    }
}
