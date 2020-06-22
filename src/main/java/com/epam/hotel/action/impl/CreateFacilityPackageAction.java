package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.dao.impl.FacilityPackageDAOImpl;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.entity.FacilityPackage;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.*;

public class CreateFacilityPackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LanguageDAO languageDAO = new LanguageDAOImpl();
        Map<Integer, String> languageMap = languageDAO.getLanguageMap();
        Map<Integer, String> facilityPackageNameMap = new HashMap<>();

        for (Integer languageId : languageMap.keySet()) {
            if (EMPTY_STRING.equals(request.getParameter(languageId.toString()))) {
                request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
                request.getRequestDispatcher(ERROR_URL).forward(request, response);
                return;
            }
            facilityPackageNameMap.put(languageId, request.getParameter(languageId.toString()));
        }

        if (request.getParameterValues(FACILITIES) == null
                || !NumericValidation.isNumeric(request.getParameter(DISCOUNT))) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        String[] facilityIdStringArray = request.getParameterValues(FACILITIES);
        List<Long> facilityIdLongList = Stream.of(facilityIdStringArray).map(Long::parseLong).collect(Collectors.toList());

        BigDecimal discount = BigDecimal.valueOf(Long.parseLong(request.getParameter(DISCOUNT)));

        FacilityDAO facilityDAO = new FacilityDAOImpl();
        List<Facility> facilityList = new ArrayList<>();

        for (long id : facilityIdLongList) {
            facilityList.add(facilityDAO.getOneById(id));
        }

        for (Integer key : languageMap.keySet()) {
            facilityPackageNameMap.put(key, request.getParameter(key.toString()));
        }

        FacilityPackageDAO facilityPackageDAO = new FacilityPackageDAOImpl();
        long packageId = facilityPackageDAO.create(new FacilityPackage(facilityPackageNameMap, facilityList, discount));

        if (packageId == -1) {
            request.setAttribute(MESSAGE, ERROR_FAILED_TO_CREATE_FACILITY_PACKAGE);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        response.sendRedirect(SHOW_FACILITY_PACKAGE_ADMIN_LIST_URL);
    }
}