package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.util.RoomAvailability;
import com.epam.hotel.validation.DateValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class EditOrderAction implements Action {
    private static final long PAID = 6L;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(String.valueOf(request.getParameter(ORDER_MAIN_ID)))) {
            request.setAttribute(MESSAGE, "Заказ не найден.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!NumericValidation.isNumeric(request.getParameter(PERSON_ID))
                || !NumericValidation.isNumeric(request.getParameter(PERSON_ID))
                || !NumericValidation.isNumeric(request.getParameter(STATUS_ID))) {
            request.setAttribute(MESSAGE, "Данные введены некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!DateValidation.isDate(request.getParameter(DATE))) {
            request.setAttribute(MESSAGE, "Дата введена некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
        long personId = Long.parseLong(request.getParameter(PERSON_ID));
        long statusId = Long.parseLong(request.getParameter(STATUS_ID));
        String date = request.getParameter(DATE);

        if (1 < statusId && statusId > 6) {
            request.setAttribute(MESSAGE, "Данные введены некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();

        if (statusId != PAID) {
            RoomAvailability.setAvailabilityForRoomsInOrder(orderMainId, Boolean.TRUE);
        } else {
            RoomAvailability.setAvailabilityForRoomsInOrder(orderMainId, Boolean.FALSE);
        }

        orderMainDAO.updateOneById(orderMainId, new OrderMain(orderMainId, personId, statusId, date));
        request.getRequestDispatcher(SHOW_ORDER_ADMIN_LIST_URL).forward(request, response);
    }
}