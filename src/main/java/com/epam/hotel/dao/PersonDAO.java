package com.epam.hotel.dao;

import com.epam.hotel.entity.Person;

public interface PersonDAO extends BaseDAO<Person> {

    Person getByFirstName(String guestFirstName);

    Person getByLastName(String guestLastName);

    Person getOneByEmail(String email);

    Person getOneByPassword(String password);

    Person getOneByPasswordAndEmail(String email, String password);

}
