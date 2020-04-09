package com.epam.hotel.dao;

import com.epam.hotel.entity.BaseEntity;

import java.util.List;

public interface BaseDAO<T extends BaseEntity> {

    void create(T entity);

    List<T> getAll();

    T getById(int id);

    void updateOneById(int id, T entity);

    void deleteOneById(int id);

}
