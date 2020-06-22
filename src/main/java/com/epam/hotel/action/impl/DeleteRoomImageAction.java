package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomImageDAO;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_IMAGE_NOT_FOUND;

public class DeleteRoomImageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(IMAGE_ID))) {
            request.setAttribute(MESSAGE, ERROR_IMAGE_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long imageId = Long.parseLong(request.getParameter(IMAGE_ID));

        RoomImageDAO roomImageDAO = new RoomImageDAOImpl();
        roomImageDAO.deleteOneById(imageId);

        request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
    }
}
