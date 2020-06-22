package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_FACILITY_NOT_FOUND;

public class EditFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(FACILITY_ID))) {
            request.setAttribute(MESSAGE, ERROR_FACILITY_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        LanguageDAO languageDAO = new LanguageDAOImpl();
        Map<Integer, String> languageMap = languageDAO.getLanguageMap();

        if (!CreateFacilityAction.facilityFieldValidation(languageMap, request, response))
            return;

        long facilityId = Long.parseLong(request.getParameter(FACILITY_ID));

        Map<Integer, String> facilityNameMap = new HashMap<>();
        Map<Integer, String> facilityDescriptionMap = new HashMap<>();

        for (Integer key : languageMap.keySet()) {
            facilityNameMap.put(key, request.getParameter(FACILITY_NAME + key.toString()));
            facilityDescriptionMap.put(key, request.getParameter(FACILITY_DESCRIPTION + key.toString()));
        }
        BigDecimal facilityPrice = BigDecimal.valueOf(Long.parseLong(request.getParameter(FACILITY_PRICE)));
        FacilityDAO facilityDAO = new FacilityDAOImpl();
        facilityDAO.updateOneById(facilityId, new Facility(facilityPrice, facilityNameMap, facilityDescriptionMap));

        response.sendRedirect(SHOW_FACILITY_ADMIN_LIST_URL);
    }
}