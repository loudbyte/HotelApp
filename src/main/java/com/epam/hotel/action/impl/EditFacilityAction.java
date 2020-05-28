package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.entity.Facility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.action.impl.ActionConstant.SHOW_FACILITY_ADMIN_LIST_URL;

public class EditFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long facilityId = Long.parseLong(request.getParameter("facility_id"));
        String facilityName = request.getParameter("facility_name");
        String facilityDescription = request.getParameter("facility_description");
        BigDecimal facilityPrice = BigDecimal.valueOf(Long.parseLong(request.getParameter("facility_price")));

        FacilityDAOImpl facilityDAO = new FacilityDAOImpl();
        facilityDAO.updateOneById(facilityId, new Facility(facilityId, facilityName, facilityPrice, facilityDescription));

        request.getRequestDispatcher(SHOW_FACILITY_ADMIN_LIST_URL).forward(request, response);
    }
}
