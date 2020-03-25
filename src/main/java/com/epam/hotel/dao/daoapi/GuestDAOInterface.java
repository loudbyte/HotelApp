package com.epam.hotel.dao.daoapi;

import com.epam.hotel.entity.Guest;

import java.util.List;

public interface GuestDAOInterface {
    List<Guest> getAllGuests();

    void setGuest(Guest guest);

}
