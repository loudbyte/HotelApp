package com.epam.hotel.dao;

import com.epam.hotel.entity.Facility;

import java.util.List;

public interface FacilityDAO extends BaseDAO<Facility> {

    List<Facility> getFacilityListByFacilityPackageId(long packageId);

}