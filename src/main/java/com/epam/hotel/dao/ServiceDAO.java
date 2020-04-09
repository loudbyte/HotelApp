package com.epam.hotel.dao;

import com.epam.hotel.entity.Service;

public interface ServiceDAO extends BaseDAO<Service>{

    Service getByServicePackageId(int servicePackageId);

    Service getByName(String name);

}
