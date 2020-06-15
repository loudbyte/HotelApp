package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.entity.FacilityPackage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class CreatePackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter(PACKAGE_NAME) == null
                || EMPTY_STRING.equals(request.getParameter(PACKAGE_NAME))
                || request.getParameterValues(FACILITIES) == null) {
            request.setAttribute(MESSAGE, "Данные введены некорректно");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        String packageName = request.getParameter(PACKAGE_NAME);
        String facilities[] = request.getParameterValues(FACILITIES);

        FacilityPackageDAOImpl orderFacilityDetailDAO = new FacilityPackageDAOImpl();
        long packageId = orderFacilityDetailDAO.create(new FacilityPackage(packageName));

        for (String facilityId : facilities) {
            orderFacilityDetailDAO.createFacilityPackageRelation(Long.parseLong(facilityId), packageId);
        }
        request.getRequestDispatcher(SHOW_PACKAGE_ADMIN_LIST_URL).forward(request, response);
    }
}