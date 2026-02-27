package com.natwest.learning.ContactApp.service;

import com.natwest.learning.ContactApp.model.Contact;

import java.util.List;

public interface ContactService {
    public boolean addContact(Contact contact);
    public List<Contact> getAllContacts();
    public void deleteContact(int id);
    public Contact getContact(int id);
}
