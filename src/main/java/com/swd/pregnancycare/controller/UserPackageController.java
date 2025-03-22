package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.entity.UserPackageEntity;
import com.swd.pregnancycare.services.UserPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/userpackage")
@RestController
@Tag(name = "User Package Management", description = "APIs for managing user-package relationships")
public class UserPackageController {

    private final UserPackageService userPackageService;

    @Autowired
    public UserPackageController(UserPackageService userPackageService) {
        this.userPackageService = userPackageService;
    }

    @Operation(summary = "Create a new user package", description = "Assigns a package to a user")
    @PostMapping
    public ResponseEntity<String> addUserPackage(@RequestBody UserPackageEntity userPackageEntity) {
        userPackageService.addNew(userPackageEntity);
        return ResponseEntity.ok("Create UserPackage successfully");
    }

    @Operation(summary = "Update an existing user package", description = "Updates a specific user package by ID")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserPackageEntity(
            @PathVariable int id,
            @RequestBody UserPackageEntity userPackageEntity
    ) {
        try {
            userPackageService.update(userPackageEntity);
            return ResponseEntity.ok("Update UserPackage successfully");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
    }

    @Operation(summary = "Delete a user package", description = "Removes a specific user package by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserPackageEntity(@PathVariable int id) {
        try {
            userPackageService.delete(id);
            return ResponseEntity.ok("Delete UserPackage successfully");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
    }
}
