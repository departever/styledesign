package com.example.service;

import com.example.entity.ContactPerson;

import java.util.List;

public interface ContactPersonService {
    List<ContactPerson> listContactPersons();

    ContactPerson getContactPersonById(Integer id);

    void deleteById(Integer id);

    void updateContactPerson(ContactPerson contactPerson);

    void addContactPerson(ContactPerson contactPerson);
}
