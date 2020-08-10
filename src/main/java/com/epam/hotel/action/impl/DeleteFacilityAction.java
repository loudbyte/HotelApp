package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class DeleteFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (NumericValidator.isNumeric(request.getParameter(FACILITY_ID))) {
            long facilityId = Long.parseLong(request.getParameter(FACILITY_ID));

            FacilityDAO facilityDAO = new FacilityDAOImpl();
            facilityDAO.deleteOneById(facilityId);

            response.sendRedirect(SHOW_FACILITY_ADMIN_LIST_JSP);
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}