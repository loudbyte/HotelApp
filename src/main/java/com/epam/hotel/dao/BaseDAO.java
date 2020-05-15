package com.epam.hotel.dao;

import com.epam.hotel.entity.BaseEntityMarker;

import java.util.List;

public interface BaseDAO<T extends BaseEntityMarker> {

    long create(T entity);

    List<T> getAll();

    T getOneById(long id);

    void updateOneById(long id, T entity);

    void deleteOneById(long id);

}
