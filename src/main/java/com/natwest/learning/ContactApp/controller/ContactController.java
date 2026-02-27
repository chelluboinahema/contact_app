package com.natwest.learning.ContactApp.controller;

import com.natwest.learning.ContactApp.model.Contact;
import com.natwest.learning.ContactApp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/data/contact")
public class ContactController {
    @Autowired
    private ContactService service;
    private ResponseEntity<?> response;

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody Contact contact){
        System.out.println("hello");
        System.out.println(contact);
        boolean state=service.addContact(contact);
        if(state){
            response=new ResponseEntity<String>("Created", HttpStatus.CREATED);
        }else{
            response=new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    @GetMapping
    public ResponseEntity<?> listAllContact(){
        List< Contact> contactList=service.getAllContacts();
        if(contactList!=null){
            response=new ResponseEntity<List<Contact>>(contactList, HttpStatus.ACCEPTED);
        }
        else {
            response=new ResponseEntity<String>("Failure",HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteContact(@PathVariable int id){
        Contact contact=service.getContact(id);
        System.out.println(contact);
        if(contact!=null){
            service.deleteContact(id);
            System.out.println(contact);
            response=new ResponseEntity<String>("Deleted",HttpStatus.FOUND);
        }
        else{
            response=new ResponseEntity<String>("NotFound",HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    @GetMapping("/{id}")
    public  ResponseEntity<?> getContact(@PathVariable String id){
        Contact contact=service.getContact(Integer.parseInt(id));
        if(contact!=null){
            response=new ResponseEntity<Contact>(contact,HttpStatus.FOUND);
        }
        else{
            response= new ResponseEntity<String>("Not Found",HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateContact(@PathVariable int id, @RequestBody Contact contact){
        Contact contact1=service.getContact(id);
        contact1.setContactId(contact.getContactId());
        contact1.setEmail(contact.getEmail());
        contact1.setName(contact.getName());
        contact1.setPhoneNo(contact.getPhoneNo());
        boolean flag=service.addContact(contact);
        if(flag){
            response=new ResponseEntity<String>("Contact Object Updated", HttpStatus.CREATED);
        }
        else{
            response=new ResponseEntity<String>("Failure",HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
