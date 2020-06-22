package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class ShowRoomsAction implements Action {

    private static final String ENCODER_PREFIX = "data:image/png;base64,";

    private static final String IMAGE_URL_DELUXE = "image_url_deluxe";
    private static final String IMAGE_URL_SUITE = "image_url_suite";
    private static final String IMAGE_URL_STANDARD = "image_url_standard";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RoomDAO roomDAO = new RoomDAOImpl();
        List<Room> roomList = roomDAO.getAll();

        Room roomDelux = null;
        Room roomSuite = null;
        Room roomStandard = null;

        for (Room room : roomList) {
            switch ((int) room.getRoomClass()) {
                case DELUXE:
                    if (room.getImageList().size() != 0 && room.getImageList().get(0).getImage() != null)
                        roomDelux = room;
                    break;
                case SUITE:
                    if (room.getImageList().size() != 0 && room.getImageList().get(0).getImage() != null)
                        roomSuite = room;
                    break;
                case STANDARD:
                    if (room.getImageList().size() != 0 && room.getImageList().get(0).getImage() != null)
                        roomStandard = room;
                    break;
            }
        }

        Encoder encoder = Base64.getEncoder();

        String urlDelux = ENCODER_PREFIX + encoder.encodeToString(roomDelux.getImageList().get(0).getImage());
        String urlSuite = ENCODER_PREFIX + encoder.encodeToString(roomSuite.getImageList().get(0).getImage());
        String urlStandard = ENCODER_PREFIX + encoder.encodeToString(roomStandard.getImageList().get(0).getImage());

        request.setAttribute(IMAGE_URL_DELUXE, urlDelux);
        request.setAttribute(IMAGE_URL_SUITE, urlSuite);
        request.setAttribute(IMAGE_URL_STANDARD, urlStandard);

        request.getRequestDispatcher(SHOW_ROOMS_URL).forward(request, response);
    }
}