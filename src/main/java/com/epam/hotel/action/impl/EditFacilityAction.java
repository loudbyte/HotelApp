package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.validation.ActionFieldValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_FACILITY_NOT_FOUND;

public class EditFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(FACILITY_ID))) {
            LanguageDAO languageDAO = new LanguageDAOImpl();
            Map<Integer, String> languageMap = languageDAO.getLanguageMap();

            if (ActionFieldValidation.isFacilityFieldValid(languageMap, request, response)) {
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

                response.sendRedirect(SHOW_FACILITY_ADMIN_LIST_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_FACILITY_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}