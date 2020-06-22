package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomImageDAO;
import com.epam.hotel.dao.impl.DAOConstant;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;
import com.epam.hotel.entity.RoomImage;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_ROOM_NOT_FOUND;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_UPLOAD_FAILED;
import static com.epam.hotel.dao.impl.DAOConstant.ERROR_ID;

public class UploadRoomImageAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(String.valueOf(request.getSession().getAttribute(ROOM_ID_FOR_UPLOAD)))) {
            request.setAttribute(MESSAGE, ERROR_ROOM_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        long roomId = Long.parseLong(String.valueOf(request.getSession().getAttribute(ROOM_ID_FOR_UPLOAD)));
        long imageId = ERROR_ID;

        if (!String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)).equals(NULL)
                || NumericValidation.isNumeric(String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)))) {
            imageId = Long.parseLong(String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)));
        }

        if (String.valueOf(request.getAttribute(IMAGE_BYTES)) != null) {
            byte[] imageBytes = (byte[]) request.getAttribute(IMAGE_BYTES);
            if (imageId != ERROR_ID) {
                RoomImageDAO roomImageDAO = new RoomImageDAOImpl();
                roomImageDAO.updateOneById(imageId, new RoomImage(imageId, imageBytes, roomId));
                response.sendRedirect(UPLOAD_IMAGE_URL);            } else {
                RoomImageDAO roomImageDAO = new RoomImageDAOImpl();
                roomImageDAO.create(new RoomImage(imageId, imageBytes, roomId));
                response.sendRedirect(UPLOAD_IMAGE_URL);
            }
        } else {
            request.setAttribute(MESSAGE, ERROR_UPLOAD_FAILED);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}