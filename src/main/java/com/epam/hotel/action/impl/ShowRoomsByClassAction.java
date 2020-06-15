package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class ShowRoomsByClassAction implements Action {

    private int roomClass;

    public ShowRoomsByClassAction(int roomClass) {
        this.roomClass = roomClass;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RoomDAOImpl roomDAO = new RoomDAOImpl();
        List<Room> roomAllList = roomDAO.getAll();
        List<Room> roomDeluxeList = new ArrayList<>();
        List<Room> roomSuiteList = new ArrayList<>();
        List<Room> roomStandardList = new ArrayList<>();
        List<Room> showRoomList = new ArrayList<>();

        for (Room room : roomAllList) {
            switch ((int) room.getRoomClassId()) {
                case DELUXE:
                    roomDeluxeList.add(room);
                    break;
                case SUITE:
                    roomSuiteList.add(room);
                    break;
                case STANDARD:
                    roomStandardList.add(room);
                    break;
            }
        }

        switch (roomClass) {
            case DELUXE:
                showRoomList = roomDeluxeList;
                break;
            case SUITE:
                showRoomList = roomSuiteList;
                break;
            case STANDARD:
                showRoomList = roomStandardList;
                break;
        }

        request.setAttribute(ROOM_LIST, showRoomList);
        request.getRequestDispatcher(SHOW_ROOMS_BY_CLASS_URL).forward(request, response);

    }
}
