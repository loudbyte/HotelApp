package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class DeletePackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(PACKAGE_ID))) {
            request.setAttribute(MESSAGE, "Данные введены некорректно");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        long packageId = Long.parseLong(request.getParameter(PACKAGE_ID));

        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();

        boolean isReferenced = orderRoomDetailDAO.getAll().stream().anyMatch(orderRoomDetail -> orderRoomDetail.getFacilityPackageId() == packageId);
        if(isReferenced) {
            request.setAttribute(MESSAGE, "Уже есть заказ с данным пакетом.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        FacilityPackageDAOImpl orderFacilityDetailDAO = new FacilityPackageDAOImpl();
        orderFacilityDetailDAO.deleteOneById(packageId);

        request.getRequestDispatcher(SHOW_PACKAGE_ADMIN_LIST_URL).forward(request, response);
    }
}