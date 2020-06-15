package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class CreateFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (EMPTY_STRING.equals(request.getParameter(FACILITY_NAME))
                || EMPTY_STRING.equals(request.getParameter(FACILITY_DESCRIPTION))
                || EMPTY_STRING.equals(request.getParameter(FACILITY_PRICE))
                || !NumericValidation.isNumeric(request.getParameter(FACILITY_PRICE))) {
            request.setAttribute(MESSAGE, "Данные введены некорректно");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        String facilityName = request.getParameter(FACILITY_NAME);
        String facilityDescription = request.getParameter(FACILITY_DESCRIPTION);
        BigDecimal facilityPrice = BigDecimal.valueOf(Long.parseLong(request.getParameter(FACILITY_PRICE)));

        FacilityDAOImpl facilityDAO = new FacilityDAOImpl();
        facilityDAO.create(new Facility(facilityName, facilityPrice, facilityDescription));

        request.getRequestDispatcher(SHOW_FACILITY_ADMIN_LIST_URL).forward(request, response);
    }
}