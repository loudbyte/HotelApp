package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class UploadRoomImageButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(ROOM_ID_FOR_UPLOAD, request.getParameter(ID));
        request.getRequestDispatcher(UPLOAD_IMAGE_URL).forward(request, response);
    }
}