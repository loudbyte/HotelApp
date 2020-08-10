package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.validation.DateValidator;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class EditOrderRoomDetailAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidator.isNumeric(request.getParameter(ORDER_ROOM_DETAIL_ID))
            && NumericValidator.isNumeric(request.getParameter(ORDER_MAIN_ID))
            && NumericValidator.isNumeric(request.getParameter(ROOM_ID))
            && NumericValidator.isNumeric(request.getParameter(FACILITY_PACKAGE_ID))
            && DateValidator.isDate(request.getParameter(START_DATE))
            && DateValidator.isDate(request.getParameter(END_DATE))) {

            long orderDetailId = Long.parseLong(request.getParameter(ORDER_ROOM_DETAIL_ID));
            long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
            long roomId = Long.parseLong(request.getParameter(ROOM_ID));
            long facilityPackageId = Long.parseLong(request.getParameter(FACILITY_PACKAGE_ID));
            String startDate = request.getParameter(START_DATE);
            String endDate = request.getParameter(END_DATE);

            RoomDAO roomDAO = new RoomDAOImpl();
            FacilityPackageDAO facilityPackageDAO = new FacilityPackageDAOImpl();

            if (facilityPackageDAO.getOneById(facilityPackageId) != null && roomDAO.getOneById(roomId) != null) {

                OrderRoomDetailDAO orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
                OrderRoomDetail orderRoomDetail = new OrderRoomDetail(orderDetailId, roomId, facilityPackageId, orderMainId, startDate, endDate);
                orderRoomDetailDAO.updateOneById(orderDetailId, orderRoomDetail);

                request.getSession().setAttribute(ORDER_MAIN_ID, orderMainId);
                response.sendRedirect(SHOW_ORDER_ROOM_DETAIL_JSP);

            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}