package com.epam.hotel.controller;

import com.epam.hotel.action.impl.UploadRoomImageAction;
import com.epam.hotel.dao.impl.RoomImageDAOImpl;
import com.epam.hotel.validation.ImageValidation;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.ERROR_URL;

public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(HotelAppController.class);
    private RoomImageDAOImpl roomImageDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long roomId = Long.parseLong(String.valueOf(request.getSession().getAttribute("id")));

        for (Part part : request.getParts()) {

            if (new ImageValidation().isImageValid(part)) {
                BufferedImage bImage = ImageIO.read(part.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                byte[] imageBytes = bos.toByteArray();

                String roomImageIdRadio = String.valueOf(request.getParameter("room_image_id_radio"));

                request.getSession().setAttribute("image_bytes", imageBytes);
                request.getSession().setAttribute("room_image_id_radio", roomImageIdRadio);

                new UploadRoomImageAction().execute(request, response);

            } else {
                request.setAttribute("message", "Upload failed");
                request.getRequestDispatcher(ERROR_URL).forward(request, response);
            }
        }
    }
}