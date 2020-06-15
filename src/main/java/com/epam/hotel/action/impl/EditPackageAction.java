package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.entity.FacilityPackage;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class EditPackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(PACKAGE_ID))) {
            request.setAttribute(MESSAGE, "Пакет не найден.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (request.getParameter(NEW_PACKAGE_NAME) == null
                || EMPTY_STRING.equals(request.getParameter(NEW_PACKAGE_NAME))
                || request.getParameterValues(FACILITIES) == null) {
            request.setAttribute(MESSAGE, "Данные введены некорректно");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long packageId = Long.parseLong(request.getParameter(PACKAGE_ID));
        String newPackageName = request.getParameter(NEW_PACKAGE_NAME);
        String[] facilities = request.getParameterValues(FACILITIES);

        FacilityPackageDAOImpl orderFacilityDetailDAO = new FacilityPackageDAOImpl();
        orderFacilityDetailDAO.deleteFacilityPackageRelationByPackageId(packageId);

        for (String facilityId : facilities) {
            orderFacilityDetailDAO.createFacilityPackageRelation(Long.parseLong(facilityId), packageId);
        }

        orderFacilityDetailDAO.updateOneById(packageId, new FacilityPackage(packageId, newPackageName));

        request.getRequestDispatcher(SHOW_PACKAGE_ADMIN_LIST_URL).forward(request, response);
    }
}