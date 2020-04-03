package com.epam.hotel.dao.daocommon;

import com.epam.hotel.entity.BaseEntity;

import java.util.List;

public interface BaseDAOInterface<T extends BaseEntity> {

    List<T> getAll();

    void create(T е);

    T getById(int id);

}
