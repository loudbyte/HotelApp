package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomImageDAO;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_IMAGE_NOT_FOUND;

public class DeleteRoomImageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidator.isNumeric(request.getParameter(IMAGE_ID))) {
            long imageId = Long.parseLong(request.getParameter(IMAGE_ID));

            RoomImageDAO roomImageDAO = new RoomImageDAOImpl();
            roomImageDAO.deleteOneById(imageId);

            response.sendRedirect(UPLOAD_IMAGE_JSP);
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_IMAGE_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}