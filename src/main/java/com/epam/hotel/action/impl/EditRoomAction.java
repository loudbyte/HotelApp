package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomClassDAO;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.RoomClassDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.validation.ActionFieldValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_CLASS;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ROOM_NOT_FOUND;

public class EditRoomAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(ROOM_ID))
                && ActionFieldValidation.isRoomFieldValid(request, response)) {

            long roomId = Long.parseLong(request.getParameter(ROOM_ID));
            int roomNumber = Integer.parseInt(request.getParameter(ROOM_NUMBER));
            int capacity = Integer.parseInt(request.getParameter(CAPACITY));
            long roomClassId = Long.parseLong(request.getParameter(ROOM_CLASS_ID));
            BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter(PRICE)));
            String availability = request.getParameter(AVAILABILITY);
            boolean isAvailable = false;

            RoomClassDAO roomClassDAO = new RoomClassDAOImpl();
            RoomDAO roomDAO = new RoomDAOImpl();

            if (roomNumber != ZERO && capacity != ZERO && price != null
                    && roomClassDAO.getOneById(roomClassId) != null) {

                roomDAO.updateOneById(roomId,
                        CreateRoomAction.defineRoomEntity(roomNumber, capacity, roomClassId, price, availability, isAvailable));

                response.sendRedirect(SHOW_ROOM_ADMIN_LIST_JSP);
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_INVALID_CLASS);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_ROOM_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}