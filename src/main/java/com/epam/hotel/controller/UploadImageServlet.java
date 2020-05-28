package com.epam.hotel.controller;

import com.epam.hotel.validation.ImageValidation;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;

public class UploadImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        for (Part part : request.getParts()) {

            if (new ImageValidation().isImageValid(part)) {
                BufferedImage bImage = ImageIO.read(part.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                byte[] imageBytes = bos.toByteArray();

                String roomImageIdRadio = String.valueOf(request.getParameter("room_image_id_radio"));

                request.setAttribute("image_bytes", imageBytes);
                request.setAttribute("room_image_id_radio", roomImageIdRadio);

                request.getRequestDispatcher("/controller/upload_room_image").forward(request, response);

            } else {
                request.setAttribute("message", "Upload failed.");
                request.getRequestDispatcher(ERROR_URL).forward(request, response);
            }
        }
    }
}