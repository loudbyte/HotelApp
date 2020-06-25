package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class DeletePersonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(PERSON_ID))) {
            long personId = Long.parseLong(request.getParameter(PERSON_ID));
            PersonDAO personDAO = new PersonDAOImpl();
            Person person = personDAO.getOneById(personId);
            if (person != null) {
                OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
                List<OrderMain> orderList = orderMainDAO.getAllByPersonId(person.getId());
                if (orderList == null || orderList.isEmpty()) {
                    personDAO.deleteOneById(personId);
                    response.sendRedirect(SHOW_PERSON_ADMIN_LIST_JSP);
                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_CANNOT_DELETE_USER_WITH_ORDERS);
                    response.sendRedirect(ERROR_JSP);
                }
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_USER_NOT_FOUND);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}