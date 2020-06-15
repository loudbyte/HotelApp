package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.util.RoomAvailability;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ORDER_MAIN_ID;

public class DeleteOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
        orderMainDAO.deleteOneById(orderMainId);

        RoomAvailability.setAvailabilityForRoomsInOrder(orderMainId, Boolean.TRUE);

        request.getRequestDispatcher(ActionConstant.SHOW_ORDER_ADMIN_LIST_URL).forward(request, response);
    }
}
