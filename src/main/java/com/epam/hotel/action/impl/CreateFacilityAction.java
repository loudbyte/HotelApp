package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.util.constant.DAOConstant;
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

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class CreateFacilityAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LanguageDAO languageDAO = new LanguageDAOImpl();
        Map<Integer, String> languageMap = languageDAO.getLanguageMap();

        if (facilityFieldValidation(languageMap, request, response)) {
            Map<Integer, String> facilityNameMap = new HashMap<>();
            Map<Integer, String> facilityDescriptionMap = new HashMap<>();

            for (Integer key : languageMap.keySet()) {
                facilityNameMap.put(key, request.getParameter(FACILITY_NAME + key.toString()));
                facilityDescriptionMap.put(key, request.getParameter(FACILITY_DESCRIPTION + key.toString()));
            }
            BigDecimal facilityPrice = BigDecimal.valueOf(Long.parseLong(request.getParameter(FACILITY_PRICE)));
            FacilityDAO facilityDAO = new FacilityDAOImpl();

            if (facilityDAO.create(new Facility(facilityPrice, facilityNameMap, facilityDescriptionMap)) != DAOConstant.ERROR_ID) {
                response.sendRedirect(SHOW_FACILITY_ADMIN_LIST_JSP);
            } else {
                request.setAttribute(MESSAGE, ERROR_FAILED_TO_CREATE_FACILITY);
                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
            }
        }
    }

    protected static boolean facilityFieldValidation(Map<Integer, String> languageMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = true;
        if (EMPTY_STRING.equals(request.getParameter(FACILITY_PRICE))
                || !NumericValidation.isNumeric(request.getParameter(FACILITY_PRICE))) {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
            result = false;
        }

        for (Integer key : languageMap.keySet()) {
            if (EMPTY_STRING.equals(request.getParameter(FACILITY_NAME + key.toString()))
                    || EMPTY_STRING.equals(request.getParameter(FACILITY_DESCRIPTION + key.toString()))) {
                request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
                response.sendRedirect(ERROR_JSP);
                result = false;
            }
        }
        return result;
    }
}