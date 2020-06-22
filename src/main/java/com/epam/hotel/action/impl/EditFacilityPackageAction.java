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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_INVALID_DATA;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_PACKAGE_NOT_FOUND;

public class EditFacilityPackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(FACILITY_PACKAGE_ID))) {
            request.setAttribute(MESSAGE, ERROR_PACKAGE_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

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

        long packageId = Long.parseLong(request.getParameter(FACILITY_PACKAGE_ID));

        String[] facilityIdStringArray = request.getParameterValues(FACILITIES);
        List<Long> facilityIdLongList = Stream.of(facilityIdStringArray).map(Long::parseLong).collect(Collectors.toList());

        BigDecimal discount = BigDecimal.valueOf(Long.parseLong(request.getParameter(DISCOUNT)));

        FacilityDAO facilityDAO = new FacilityDAOImpl();
        List<Facility> facilityList = new ArrayList<>();

        for (long id : facilityIdLongList) {
            facilityList.add(facilityDAO.getOneById(id));
        }

        FacilityPackageDAO facilityPackageDAO = new FacilityPackageDAOImpl();
        facilityPackageDAO.updateOneById(packageId, new FacilityPackage(facilityPackageNameMap, facilityList, discount));

        response.sendRedirect(SHOW_FACILITY_PACKAGE_ADMIN_LIST_URL);
    }
}