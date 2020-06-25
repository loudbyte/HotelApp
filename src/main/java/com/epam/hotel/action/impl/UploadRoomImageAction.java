package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomImageDAO;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;
import com.epam.hotel.entity.RoomImage;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ROOM_NOT_FOUND;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_UPLOAD_FAILED;

public class UploadRoomImageAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(String.valueOf(request.getSession().getAttribute(ROOM_ID_FOR_UPLOAD)))) {
            long roomId = Long.parseLong(String.valueOf(request.getSession().getAttribute(ROOM_ID_FOR_UPLOAD)));
            long imageId = ZERO;

            if (NumericValidation.isNumeric(String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)))) {
                imageId = Long.parseLong(String.valueOf(request.getAttribute(ROOM_IMAGE_ID_RADIO)));
            }

            if (String.valueOf(request.getAttribute(IMAGE_BYTES)) != null) {
                byte[] imageBytes = (byte[]) request.getAttribute(IMAGE_BYTES);
                RoomImageDAO roomImageDAO = new RoomImageDAOImpl();
                if (imageId != ZERO) {
                    roomImageDAO.updateOneById(imageId, new RoomImage(imageId, imageBytes, roomId));
                } else {
                    roomImageDAO.create(new RoomImage(imageId, imageBytes, roomId));
                }
                response.sendRedirect(UPLOAD_IMAGE_JSP);
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_UPLOAD_FAILED);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_ROOM_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}