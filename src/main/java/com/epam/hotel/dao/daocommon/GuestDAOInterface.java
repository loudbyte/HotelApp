package com.epam.hotel.dao.daocommon;

import com.epam.hotel.entity.Guest;

public interface GuestDAOInterface extends BaseDAOInterface<Guest> {

    Guest getByFirstName(String guestFirstName);

    Guest getByLastName(String guestLastName);

}
