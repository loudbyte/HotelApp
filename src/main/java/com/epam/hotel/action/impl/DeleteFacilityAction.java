package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.SHOW_FACILITY_ADMIN_LIST_URL;

public class DeleteFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long facilityId = Long.parseLong(request.getParameter("facility_id"));

        FacilityDAOImpl facilityDAO = new FacilityDAOImpl();
        facilityDAO.deleteOneById(facilityId);

        request.getRequestDispatcher(SHOW_FACILITY_ADMIN_LIST_URL).forward(request, response);
    }
}