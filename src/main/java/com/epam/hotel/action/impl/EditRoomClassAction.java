package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.RoomClassDAO;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.dao.impl.RoomClassDAOImpl;
import com.epam.hotel.entity.RoomClass;
import com.epam.hotel.validation.ActionFieldValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ROOM_CLASS_NOT_FOUND;

public class EditRoomClassAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(ROOM_CLASS_ID))) {

            LanguageDAO languageDAO = new LanguageDAOImpl();
            Map<Integer, String> languageMap = languageDAO.getLanguageMap();

            if (ActionFieldValidation.isRoomClassFieldValid(languageMap, request, response)) {

                long roomClassId = Long.parseLong(request.getParameter(ROOM_CLASS_ID));

                Map<Integer, String> roomClassNameMap = new HashMap<>();
                Map<Integer, String> roomClassDescriptionMap = new HashMap<>();

                for (Integer key : languageMap.keySet()) {
                    roomClassNameMap.put(key, request.getParameter(ROOM_CLASS_NAME + key.toString()));
                    roomClassDescriptionMap.put(key, request.getParameter(ROOM_CLASS_DESCRIPTION + key.toString()));
                }
                RoomClassDAO roomClassDAO = new RoomClassDAOImpl();
                roomClassDAO.updateOneById(roomClassId, new RoomClass(roomClassNameMap, roomClassDescriptionMap));

                response.sendRedirect(SHOW_ROOM_CLASS_ADMIN_LIST_JSP);

            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_ROOM_CLASS_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}
