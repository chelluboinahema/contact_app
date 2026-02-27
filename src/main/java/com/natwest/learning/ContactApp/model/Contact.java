package com.natwest.learning.ContactApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contact {
    @Id
    private int contactId;
    private String email;
    private String phoneNo;
    private String name;

    public Contact() {
    }

    public Contact(int contactId, String name, String email, String phoneNo) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
