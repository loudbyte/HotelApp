package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ALREADY_HAVE_ORDER_WITH_THIS_PACKAGE;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class DeleteFacilityPackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(FACILITY_PACKAGE_ID))) {
            long packageId = Long.parseLong(request.getParameter(FACILITY_PACKAGE_ID));

            OrderRoomDetailDAO orderRoomDetailDAO = new OrderRoomDetailDAOImpl();

            boolean isReferenced = orderRoomDetailDAO.getAll().stream().anyMatch(orderRoomDetail -> orderRoomDetail.getFacilityPackageId() == packageId);
            if(!isReferenced) {

                FacilityPackageDAO orderFacilityDetailDAO = new FacilityPackageDAOImpl();
                orderFacilityDetailDAO.deleteOneById(packageId);

                response.sendRedirect(SHOW_FACILITY_PACKAGE_ADMIN_LIST_JSP);

            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ALREADY_HAVE_ORDER_WITH_THIS_PACKAGE);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}