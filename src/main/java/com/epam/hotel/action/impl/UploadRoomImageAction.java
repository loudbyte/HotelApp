package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;
import com.epam.hotel.entity.RoomImage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.UPLOAD_IMAGE_URL;

public class UploadRoomImageAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long roomId = Long.parseLong(String.valueOf(request.getSession().getAttribute("id")));
        long imageId = 0;

        if (!String.valueOf(request.getSession().getAttribute("room_image_id_radio")).equals("null")) {
            imageId = Long.parseLong(String.valueOf(request.getSession().getAttribute("room_image_id_radio")));
        }

        byte[] imageBytes = (byte[]) request.getSession().getAttribute("image_bytes");

        if (imageId != 0) {
            RoomImageDAOImpl roomImageDAO = new RoomImageDAOImpl();
            roomImageDAO.updateOneById(imageId, new RoomImage(1, imageBytes, roomId));
            request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);

        } else {
            RoomImageDAOImpl roomImageDAO = new RoomImageDAOImpl();
            roomImageDAO.create(new RoomImage(1, imageBytes, roomId));
            request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
        }
        request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
    }
}
