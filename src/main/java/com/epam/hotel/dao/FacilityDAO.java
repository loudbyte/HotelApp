package com.epam.hotel.dao;

import com.epam.hotel.entity.Facility;

public interface FacilityDAO extends BaseDAO<Facility>{

    Facility getByName(String name);
}
