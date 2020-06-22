package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_ALREADY_HAVE_ORDER_WITH_THIS_PACKAGE;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_INVALID_DATA;

public class DeleteFacilityPackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(FACILITY_PACKAGE_ID))) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        long packageId = Long.parseLong(request.getParameter(FACILITY_PACKAGE_ID));

        OrderRoomDetailDAO orderRoomDetailDAO = new OrderRoomDetailDAOImpl();

        boolean isReferenced = orderRoomDetailDAO.getAll().stream().anyMatch(orderRoomDetail -> orderRoomDetail.getFacilityPackageId() == packageId);
        if(isReferenced) {
            request.setAttribute(MESSAGE, ERROR_ALREADY_HAVE_ORDER_WITH_THIS_PACKAGE);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        FacilityPackageDAO orderFacilityDetailDAO = new FacilityPackageDAOImpl();
        orderFacilityDetailDAO.deleteOneById(packageId);

        response.sendRedirect(SHOW_FACILITY_PACKAGE_ADMIN_LIST_URL);
    }
}