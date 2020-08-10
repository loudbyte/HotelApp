package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.RoomClassDAO;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.dao.impl.RoomClassDAOImpl;
import com.epam.hotel.entity.RoomClass;
import com.epam.hotel.util.constant.DAOConstant;
import com.epam.hotel.validation.ActionFieldValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class CreateRoomClassAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LanguageDAO languageDAO = new LanguageDAOImpl();
        Map<Integer, String> languageMap = languageDAO.getLanguageMap();

        if (ActionFieldValidator.isRoomClassFieldValid(languageMap, request, response)) {

            Map<Integer, String> roomClassNameMap = new HashMap<>();
            Map<Integer, String> roomClassDescriptionMap = new HashMap<>();

            for (Integer key : languageMap.keySet()) {
                roomClassNameMap.put(key, request.getParameter(ROOM_CLASS_NAME + key.toString()));
                roomClassDescriptionMap.put(key, request.getParameter(ROOM_CLASS_DESCRIPTION + key.toString()));
            }
            RoomClassDAO roomClassDAO = new RoomClassDAOImpl();

            if (roomClassDAO.create(new RoomClass(roomClassNameMap, roomClassDescriptionMap)) != DAOConstant.ERROR_ID) {
                response.sendRedirect(SHOW_ROOM_CLASS_ADMIN_LIST_JSP);
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_FAILED_TO_CREATE_ROOM_CLASS);
                response.sendRedirect(ERROR_JSP);
            }
        }
    }

}
