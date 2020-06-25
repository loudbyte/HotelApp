package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;

public class UploadRoomImageButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(ROOM_ID_FOR_UPLOAD, request.getParameter(ROOM_ID_FOR_UPLOAD));
        response.sendRedirect(UPLOAD_IMAGE_JSP);
    }
}