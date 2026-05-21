package com.natwest.learning.ContactApp.controller;

import com.natwest.learning.ContactApp.model.Contact;
import com.natwest.learning.ContactApp.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/contact")
@Tag(name = "Contact Controller", description = "APIs for managing contacts")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    // ---------------- CREATE CONTACT ----------------
    @PostMapping
    @Operation(
            summary = "Create Contact",
            description = "Creates a new contact"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<String> addContact(@RequestBody Contact contact) {

        boolean created = service.addContact(contact);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Contact created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create contact");
        }
    }

    // ---------------- GET ALL CONTACTS ----------------
    @GetMapping
    @Operation(
            summary = "Get all contacts",
            description = "Fetches list of all contacts"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts fetched successfully")
    })
    public ResponseEntity<List<Contact>> getAllContacts() {

        List<Contact> contacts = service.getAllContacts();

        return ResponseEntity.ok(contacts);
    }

    // ---------------- GET CONTACT BY ID ----------------
    @GetMapping("/{id}")
    @Operation(
            summary = "Get contact by ID",
            description = "Fetch a single contact using ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact found"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<?> getContactById(@PathVariable int id) {

        Contact contact = service.getContact(id);

        if (contact != null) {
            return ResponseEntity.ok(contact);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contact not found");
        }
    }

    // ---------------- DELETE CONTACT ----------------
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete contact",
            description = "Deletes a contact by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<String> deleteContact(@PathVariable int id) {

        Contact contact = service.getContact(id);

        if (contact != null) {
            service.deleteContact(id);
            return ResponseEntity.ok("Contact deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contact not found");
        }
    }

    // ---------------- UPDATE CONTACT ----------------
    @PutMapping("/{id}")
    @Operation(
            summary = "Update contact",
            description = "Updates an existing contact by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact updated"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<String> updateContact(
            @PathVariable int id,
            @RequestBody Contact updatedContact) {

        Contact existingContact = service.getContact(id);

        if (existingContact == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contact not found");
        }

        // update fields (DO NOT override ID)
        existingContact.setName(updatedContact.getName());
        existingContact.setEmail(updatedContact.getEmail());
        existingContact.setPhoneNo(updatedContact.getPhoneNo());

        service.addContact(existingContact); // or service.updateContact()

        return ResponseEntity.ok("Contact updated successfully");
    }
}
