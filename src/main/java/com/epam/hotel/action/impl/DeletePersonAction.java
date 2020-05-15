package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.ERROR_URL;

public class DeletePersonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long id = Long.parseLong(request.getParameter("person_id"));
        PersonDAOImpl personDAO = new PersonDAOImpl();
        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
        Person person = personDAO.getOneById(id);
        List<OrderMain> orderList = null;
        if (person != null) {
            orderList = orderMainDAO.getAllByPersonId(person.getId());
            if (orderList == null || orderList.isEmpty()) {
                personDAO.deleteOneById(id);
                new ShowPersonAdminListAction().execute(request, response);
                return;
            }
            request.setAttribute("message", "Нелья удалить пользователя, так как у него есть заказы.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        } else {
            request.setAttribute("message", "Пользователя не существует.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}
