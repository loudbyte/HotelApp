package com.epam.hotel.dao;

import com.epam.hotel.entity.BaseEntity;

import java.util.List;

public interface BaseDAO<T extends BaseEntity> {

    long create(T entity);

    List<T> getAll();

    T getOneById(long id);

    void updateOneById(long id, T entity);

    void deleteOneById(long id);

}
