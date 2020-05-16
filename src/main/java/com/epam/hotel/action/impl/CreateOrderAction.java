package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.epam.hotel.action.impl.Constant.*;

public class CreateOrderAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = null;

        if (request.getSession().getAttribute("person") instanceof Person) {
            person = (Person) request.getSession().getAttribute("person");
        } else {
            request.setAttribute("message", "Необходимо зарегестрироваться");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long roomId = Long.parseLong(request.getParameter("room_id"));
        long personId = person.getId();
        long detailId = Long.parseLong(request.getParameter("detail_id"));
        LocalDate localDateNow = LocalDate.now();

        OrderMain orderMain = new OrderMain();
        OrderRoomDetail orderRoomDetail = new OrderRoomDetail();

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();

        orderMain.setPersonId(personId);
        orderMain.setStatusId(NEW);
        orderMain.setDate(String.valueOf(localDateNow));

        orderMain.setId(orderMainDAO.create(orderMain));

        orderRoomDetail.setRoomId(roomId);
        orderRoomDetail.setOrderFacilityDetailId(detailId);
        orderRoomDetail.setOrderMainId(orderMain.getId());
        orderRoomDetail.setStartDate(request.getParameter("start_date"));
        orderRoomDetail.setEndDate(request.getParameter("end_date"));
        orderRoomDetail.setId(orderRoomDetailDAO.create(orderRoomDetail));

        request.setAttribute("my_orders", orderMainDAO.getAllByPersonId(personId));
        request.getRequestDispatcher(SHOW_MY_ORDERS_URL).forward(request, response);
    }
}
