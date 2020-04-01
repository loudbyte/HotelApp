package com.epam.hotel.dao.daocommon;

import com.epam.hotel.entity.BaseEntity;

import java.util.List;

public interface BaseDAOInterface {

    List<? extends BaseEntity> getAll();

}
