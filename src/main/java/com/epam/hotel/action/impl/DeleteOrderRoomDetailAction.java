package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.role.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class DeleteOrderRoomDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long orderDetailId = Long.parseLong(request.getParameter(ORDER_ROOM_DETAIL_ID));
        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
        long roomId = Long.parseLong(request.getParameter(ActionConstant.ROOM_ID));

        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        Room room = roomDAO.getOneById(roomId);
        room.setAvailability(Boolean.TRUE);
        roomDAO.updateOneById(roomId, room);
        orderRoomDetailDAO.deleteOneById(orderDetailId);

        request.setAttribute(ORDER_MAIN_ID, orderMainId);

        if (request.getSession().getAttribute(ROLE) == Role.ADMIN) {
            request.getRequestDispatcher(ActionConstant.SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
            return;
        }
        request.getRequestDispatcher(ActionConstant.SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
    }
}