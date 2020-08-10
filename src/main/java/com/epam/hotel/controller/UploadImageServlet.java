package com.epam.hotel.controller;

import com.epam.hotel.validation.ImageValidator;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.ERROR_JSP;
import static com.epam.hotel.util.constant.ActionConstant.MESSAGE;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class UploadImageServlet extends HttpServlet {

    private static final String JPG = "jpg";
    private static final String ROOM_IMAGE_ID_RADIO = "room_image_id_radio";
    private static final String IMAGE_BYTES = "image_bytes";
    private static final String CONTROLLER_UPLOAD_ROOM_IMAGE = "/controller/upload_room_image";
    private static final String IMAGE_FILE = "image_file";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart(IMAGE_FILE);
        if (ImageValidator.isImageValid(part)) {
            BufferedImage bufferedImage = ImageIO.read(part.getInputStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, JPG, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            String roomImageIdRadio = String.valueOf(request.getParameter(ROOM_IMAGE_ID_RADIO));

            request.setAttribute(IMAGE_BYTES, imageBytes);
            request.setAttribute(ROOM_IMAGE_ID_RADIO, roomImageIdRadio);

            request.getRequestDispatcher(CONTROLLER_UPLOAD_ROOM_IMAGE).forward(request, response);

        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}