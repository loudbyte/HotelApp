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

public class DeleteOrderRoomDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long orderDetailId = Long.parseLong(request.getParameter("order_room_detail_id"));
        long orderMainId = Long.parseLong(request.getParameter("order_main_id"));
        long roomId = Long.parseLong(request.getParameter("room_id"));

        // TODO if role is ADMIN - delete_order_detail_action (in jsp),
        //  else if role != ADMIN - delete_order_detail_action if order status_id = new, else button not available
        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        Room room = roomDAO.getOneById(roomId);
        room.setAvailability(Boolean.TRUE);
        roomDAO.updateOneById(roomId, room);
        orderRoomDetailDAO.deleteOneById(orderDetailId);

        request.setAttribute("order_main_id", orderMainId);

        if (request.getSession().getAttribute("role") == Role.ADMIN) {
            request.getRequestDispatcher(ActionConstant.SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
            return;
        }
        request.getRequestDispatcher(ActionConstant.SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
    }
}
