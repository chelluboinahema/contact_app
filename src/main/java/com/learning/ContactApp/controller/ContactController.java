package com.learning.ContactApp.controller;

import com.learning.ContactApp.model.Contact;
import com.learning.ContactApp.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/data/contact")
@Tag(name = "Contact API", description = "APIs for managing contacts (CRUD operations secured with JWT)")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    // ---------------- CREATE CONTACT ----------------
    @PostMapping
    @Operation(
            summary = "Create a new contact",
            description = "This API creates a new contact entry in the system. " +
                    "Requires JWT authentication. Provide contact details in request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Access denied")
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
            description = "Fetches the complete list of all contacts stored in the system. Requires valid JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    // ---------------- GET CONTACT BY ID ----------------
    @GetMapping("/{id}")
    @Operation(
            summary = "Get contact by ID",
            description = "Fetches a specific contact using its unique ID. Requires JWT authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact found successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
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
            summary = "Delete a contact",
            description = "Deletes a contact permanently from the system using its ID. Requires valid JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
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
            summary = "Update an existing contact",
            description = "Updates contact details (name, email, phone number) using contact ID. Requires JWT authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact updated successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<String> updateContact(
            @PathVariable int id,
            @RequestBody Contact updatedContact) {

        Contact existingContact = service.getContact(id);

        if (existingContact == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contact not found");
        }

        existingContact.setName(updatedContact.getName());
        existingContact.setEmail(updatedContact.getEmail());
        existingContact.setPhoneNo(updatedContact.getPhoneNo());

        service.addContact(existingContact);

        return ResponseEntity.ok("Contact updated successfully");
    }
}