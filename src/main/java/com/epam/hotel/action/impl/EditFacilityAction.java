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

public class EditFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(FACILITY_ID))) {
            request.setAttribute(MESSAGE, "Услуга не найдена");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if ("".equals(request.getParameter(FACILITY_NAME))
                || "".equals(request.getParameter(FACILITY_DESCRIPTION))
                || "".equals(request.getParameter(FACILITY_PRICE))
                || !NumericValidation.isNumeric(request.getParameter(FACILITY_PRICE))) {
            request.setAttribute(MESSAGE, "Данные введены некорректно");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long facilityId = Long.parseLong(request.getParameter(FACILITY_ID));
        String facilityName = request.getParameter(FACILITY_NAME);
        String facilityDescription = request.getParameter(FACILITY_DESCRIPTION);
        BigDecimal facilityPrice = BigDecimal.valueOf(Long.parseLong(request.getParameter(FACILITY_PRICE)));

        FacilityDAOImpl facilityDAO = new FacilityDAOImpl();
        facilityDAO.updateOneById(facilityId, new Facility(facilityId, facilityName, facilityPrice, facilityDescription));

        request.getRequestDispatcher(SHOW_FACILITY_ADMIN_LIST_URL).forward(request, response);
    }
}