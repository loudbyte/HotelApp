package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class DeletePersonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(PERSON_ID))) {
            request.setAttribute(MESSAGE, "Пользователь не найден.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long personId = Long.parseLong(request.getParameter(PERSON_ID));
        PersonDAOImpl personDAO = new PersonDAOImpl();
        Person person = personDAO.getOneById(personId);
        if (person != null) {
            OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
            List<OrderMain> orderList = orderMainDAO.getAllByPersonId(person.getId());
            if (orderList == null || orderList.isEmpty()) {
                personDAO.deleteOneById(personId);
                request.getRequestDispatcher(SHOW_PERSON_ADMIN_LIST_URL).forward(request, response);
                return;
            }
            request.setAttribute(MESSAGE, "Нелья удалить пользователя, так как у него есть заказы.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        } else {
            request.setAttribute(MESSAGE, "Пользователя не существует.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}