package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.UPLOAD_IMAGE_URL;

public class DeleteRoomImageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long imageId = Long.parseLong(request.getParameter("image_id"));

        RoomImageDAOImpl roomImageDAO = new RoomImageDAOImpl();
        roomImageDAO.deleteOneById(imageId);

        request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
    }
}
