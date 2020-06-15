package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;
import com.epam.hotel.entity.RoomImage;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class UploadRoomImageAction implements Action {

    public static final int FAKE_ID = 0;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(String.valueOf(request.getSession().getAttribute(ROOM_ID_FOR_UPLOAD)))) {
            request.setAttribute(MESSAGE, "Комната не найдена");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        long roomId = Long.parseLong(String.valueOf(request.getSession().getAttribute(ROOM_ID_FOR_UPLOAD)));
        long imageId = FAKE_ID;

        if (!String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)).equals(NULL)
                || NumericValidation.isNumeric(String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)))) {
            imageId = Long.parseLong(String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)));
        }

        if (String.valueOf(request.getAttribute(IMAGE_BYTES)) != null) {
            byte[] imageBytes = (byte[]) request.getAttribute(IMAGE_BYTES);
            if (imageId != FAKE_ID) {
                RoomImageDAOImpl roomImageDAO = new RoomImageDAOImpl();
                roomImageDAO.updateOneById(imageId, new RoomImage(imageId, imageBytes, roomId));
                request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
            } else {
                RoomImageDAOImpl roomImageDAO = new RoomImageDAOImpl();
                roomImageDAO.create(new RoomImage(imageId, imageBytes, roomId));
                request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
            }
        } else {
            request.setAttribute(MESSAGE, "Изображение не загрузилось.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
    }
}