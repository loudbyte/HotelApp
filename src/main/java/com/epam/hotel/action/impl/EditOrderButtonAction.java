package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.EDIT_ORDER_URL;

public class EditOrderButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderId = Long.parseLong(request.getParameter("order_id"));
        long personId = Long.parseLong(request.getParameter("person_id"));
        long statusId = Long.parseLong(request.getParameter("status_id"));
        String date = request.getParameter("date");

        request.setAttribute("order_id", orderId);
        request.setAttribute("person_id", personId);
        request.setAttribute("status_id", statusId);
        request.setAttribute("date", date);
        request.getRequestDispatcher(EDIT_ORDER_URL).forward(request, response);
    }
}
