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
import com.epam.hotel.validation.ActionFieldValidation;
import com.epam.hotel.validation.NumericValidation;

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

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_PACKAGE_NOT_FOUND;

public class EditFacilityPackageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(FACILITY_PACKAGE_ID))) {
            LanguageDAO languageDAO = new LanguageDAOImpl();
            Map<Integer, String> languageMap = languageDAO.getLanguageMap();
            Map<Integer, String> facilityPackageNameMap = new HashMap<>();

            if (ActionFieldValidation.isFacilityPackageFieldValid(languageMap, request, response)) {

                for (Integer languageId : languageMap.keySet()) {
                    facilityPackageNameMap.put(languageId, request.getParameter(languageId.toString()));
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

                response.sendRedirect(SHOW_FACILITY_PACKAGE_ADMIN_LIST_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_PACKAGE_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}