package com.natwest.learning.ContactApp.service;

import com.natwest.learning.ContactApp.dao.ContactDao;
import com.natwest.learning.ContactApp.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDao dao;

    @Override
    public boolean addContact(Contact contact) {
        Contact c=dao.save(contact);
        System.out.println(c);
        if (c!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<Contact> getAllContacts() {
        return dao.findAll();
    }

    @Override
    public void deleteContact(int id) {
        dao.deleteById(id);
    }

    @Override
    public Contact getContact(int id) {
        return dao.findById(id).get();
    }
}
