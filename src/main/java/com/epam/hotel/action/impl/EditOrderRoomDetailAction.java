package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.validation.DateValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.*;

public class EditOrderRoomDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(ORDER_ROOM_DETAIL_ID))) {
            request.setAttribute(MESSAGE, ERROR_ORDER_DETAIL_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {
            request.setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!NumericValidation.isNumeric(request.getParameter(ROOM_ID))
                || !NumericValidation.isNumeric(request.getParameter(FACILITY_PACKAGE_ID))
                || !DateValidation.isDate(request.getParameter(START_DATE))
                || !DateValidation.isDate(request.getParameter(END_DATE))) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderDetailId = Long.parseLong(request.getParameter(ORDER_ROOM_DETAIL_ID));
        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
        long roomId = Long.parseLong(request.getParameter(ROOM_ID));
        long facilityPackageId = Long.parseLong(request.getParameter(FACILITY_PACKAGE_ID));
        String startDate = request.getParameter(START_DATE);
        String endDate = request.getParameter(END_DATE);

        FacilityPackageDAO facilityPackageDAO = new FacilityPackageDAOImpl();
        int facilityPackageAmount = facilityPackageDAO.getAll().size();

        if (facilityPackageId > facilityPackageAmount || facilityPackageId < 1) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        OrderRoomDetailDAO orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        OrderRoomDetail orderRoomDetail = new OrderRoomDetail(orderDetailId, roomId, facilityPackageId, orderMainId, startDate, endDate);
        orderRoomDetailDAO.updateOneById(orderDetailId, orderRoomDetail);

        request.setAttribute(ORDER_MAIN_ID, orderMainId);

        request.getRequestDispatcher(SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
    }
}