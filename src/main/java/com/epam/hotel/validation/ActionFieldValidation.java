package com.epam.hotel.validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_EMPTY_FIELDS;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class ActionFieldValidation {
    public static boolean isFacilityFieldValid(Map<Integer, String> languageMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    public static boolean isFacilityPackageFieldValid(Map<Integer, String> languageMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = true;
        if (request.getParameterValues(FACILITIES) == null
                || !NumericValidation.isNumeric(request.getParameter(DISCOUNT))) {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
            result = false;
        }

        for (Integer languageId : languageMap.keySet()) {
            if (EMPTY_STRING.equals(request.getParameter(languageId.toString()))) {
                request.getSession().setAttribute(MESSAGE, ERROR_EMPTY_FIELDS);
                response.sendRedirect(ERROR_JSP);
                result = false;
            }
        }
        return result;
    }

    public static boolean isOrderStatusFieldValid(Map<Integer, String> languageMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = true;
        for (Integer key : languageMap.keySet()) {
            if (EMPTY_STRING.equals(request.getParameter(ORDER_STATUS_NAME + key.toString()))) {
                request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
                response.sendRedirect(ERROR_JSP);
                result = false;
            }
        }
        return result;
    }

    public static boolean isRoomFieldValid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = true;
        if (!NumericValidation.isNumeric(request.getParameter(ROOM_NUMBER))
                || !NumericValidation.isNumeric(request.getParameter(CAPACITY))
                || !NumericValidation.isNumeric(request.getParameter(ROOM_CLASS_ID))
                || !NumericValidation.isNumeric(request.getParameter(PRICE))) {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
            result = false;
        }
        return result;
    }

    public static boolean isRoomClassFieldValid(Map<Integer, String> languageMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = true;
        for (Integer key : languageMap.keySet()) {
            if (EMPTY_STRING.equals(request.getParameter(ROOM_CLASS_NAME + key.toString()))
                    || EMPTY_STRING.equals(request.getParameter(ROOM_CLASS_DESCRIPTION + key.toString()))) {
                request.getSession().setAttribute(MESSAGE, ERROR_EMPTY_FIELDS);
                response.sendRedirect(ERROR_JSP);
                result = false;
            }
        }
        return result;
    }
}
