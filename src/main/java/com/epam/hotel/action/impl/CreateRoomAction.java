package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomClassDAO;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.RoomClassDAOImpl;
import com.epam.hotel.util.constant.DAOConstant;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.validation.ActionFieldValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class CreateRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (ActionFieldValidation.isRoomFieldValid(request, response)) {
            int roomNumber = Integer.parseInt(request.getParameter(ROOM_NUMBER));
            int capacity = Integer.parseInt(request.getParameter(CAPACITY));
            long roomClassId = Long.parseLong(request.getParameter(ROOM_CLASS_ID));
            BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter(PRICE)));
            String availability = request.getParameter(AVAILABILITY);
            boolean isAvailable = false;

            System.out.println(availability);

            RoomClassDAO roomClassDAO = new RoomClassDAOImpl();
            RoomDAO roomDAO = new RoomDAOImpl();
            List<Room> roomList = roomDAO.getAll();

            if (roomList.stream().allMatch(room -> room.getRoomNumber() != roomNumber)
                && roomNumber != ZERO && capacity != ZERO && price != null
                && roomClassDAO.getOneById(roomClassId) != null) {

                if (roomDAO.create(defineRoomEntity(roomNumber, capacity, roomClassId, price, availability, isAvailable))
                        != DAOConstant.ERROR_ID) {

                    response.sendRedirect(SHOW_ROOM_ADMIN_LIST_JSP);

                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_ALREADY_HAVE_ORDER_WITH_THIS_PACKAGE);
                    response.sendRedirect(ERROR_JSP);
                }
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_EMPTY_FIELDS);
                response.sendRedirect(ERROR_JSP);
            }
        }
    }

    static Room defineRoomEntity(int roomNumber, int capacity,
                                 long roomClassId, BigDecimal price, String availability, boolean isAvailable) {
        if (ON.equals(availability)) {
            isAvailable = true;
        }

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);
        room.setRoomClassId(roomClassId);
        room.setPrice(price);
        room.setAvailability(isAvailable);
        return room;
    }

}