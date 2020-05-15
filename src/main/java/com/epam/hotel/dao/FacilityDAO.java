package com.epam.hotel.dao;

import com.epam.hotel.entity.Facility;
import com.epam.hotel.entity.FacilityPackageRelation;

import java.util.List;

public interface FacilityDAO extends BaseDAO<Facility>{

    Facility getByName(String name);
}
