package com.epam.hotel.dao.interfaces;

import com.epam.hotel.entity.Guest;

import java.util.List;

public interface GuestDAOInterface {
    List<Guest> getGuests();

    void setGuest(Guest guest);

}
