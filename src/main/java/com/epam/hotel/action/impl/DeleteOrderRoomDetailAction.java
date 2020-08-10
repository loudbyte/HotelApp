package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class DeleteOrderRoomDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidator.isNumeric(request.getParameter(ORDER_ROOM_DETAIL_ID))
                && NumericValidator.isNumeric(request.getParameter(ORDER_MAIN_ID))) {

            long orderDetailId = Long.parseLong(request.getParameter(ORDER_ROOM_DETAIL_ID));
            long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

            OrderRoomDetailDAO orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
            orderRoomDetailDAO.deleteOneById(orderDetailId);

            request.getSession().setAttribute(ORDER_MAIN_ID, orderMainId);
            response.sendRedirect(SHOW_ORDER_ROOM_DETAIL_JSP);

        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}