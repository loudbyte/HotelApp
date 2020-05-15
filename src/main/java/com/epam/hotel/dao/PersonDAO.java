package com.epam.hotel.dao;

import com.epam.hotel.entity.Person;

public interface PersonDAO extends BaseDAO<Person> {

    Person getByFirstName(String guestFirstName);

    Person getByLastName(String guestLastName);

    Person getOneByEmail(String email);

    Person getOneByPassword(String password);

    public Person getOneByPasswordAndEmail(String email, String password);

}
