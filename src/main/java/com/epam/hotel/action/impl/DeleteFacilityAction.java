package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;
import static com.epam.hotel.action.impl.ErrorConstant.*;

public class DeleteFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!NumericValidation.isNumeric(request.getParameter(FACILITY_ID))) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        long facilityId = Long.parseLong(request.getParameter(FACILITY_ID));

        FacilityDAO facilityDAO = new FacilityDAOImpl();
        facilityDAO.deleteOneById(facilityId);

        response.sendRedirect(SHOW_FACILITY_ADMIN_LIST_URL);
    }
}