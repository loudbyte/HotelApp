package com.epam.hotel.entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private List<Room> rooms = new ArrayList();
    private List<Guest> guests = new ArrayList();

    public List<Guest> getGuests() {
        return guests;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
