package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RoomDAOImpl roomDAO = new RoomDAOImpl();
        List<Room> roomList = roomDAO.getAll();

        Room roomDelux = null;
        Room roomSuite = null;
        Room roomStandard = null;

        for (Room room : roomList) {
            switch ((int) room.getRoomClassId()) {
                case DELUX:
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

        String urlDelux = "data:image/png;base64," + encoder.encodeToString(roomDelux.getImageList().get(0).getImage());
        String urlSuite = "data:image/png;base64," + encoder.encodeToString(roomSuite.getImageList().get(0).getImage());
        String urlStandard = "data:image/png;base64," + encoder.encodeToString(roomStandard.getImageList().get(0).getImage());

        request.setAttribute("image_url_delux", urlDelux);
        request.setAttribute("image_url_suite", urlSuite);
        request.setAttribute("image_url_standard", urlStandard);

        request.getRequestDispatcher(SHOW_ROOMS_URL).forward(request, response);
    }
}