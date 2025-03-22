package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.services.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/package")
@CrossOrigin
@RestController
@Tag(name = "Package Management", description = "APIs for managing pregnancy care packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Operation(summary = "Create a new package", description = "Adds a new package to the system")
    @PostMapping
    public ResponseEntity<String> addPackage(@RequestBody PackageEntity packageEntity) {
        packageService.addNew(packageEntity);
        return ResponseEntity.ok("Create Package successfully");
    }

    @Operation(summary = "Get all packages", description = "Retrieves a list of all available packages")
    @GetMapping
    public ResponseEntity<?> getPackages() {
        var packageList = packageService.getAll();
        return ResponseEntity.ok(packageList);
    }

    @Operation(summary = "Get a package by ID", description = "Retrieves a specific package by its ID")
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("{id}")
    public ResponseEntity<?> getPackagesByID(@PathVariable int id) {
        var packages = packageService.getbyId(id);
        return ResponseEntity.ok(packages);
    }

    @Operation(summary = "Delete a package", description = "Deletes a package by its ID")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePackage(@PathVariable int id) {
        try {
            packageService.deleteById(id);
            return ResponseEntity.ok("Delete Package successfully");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package not found");
        }
    }

    @Operation(summary = "Update a package", description = "Updates an existing package")
    @PutMapping
    public ResponseEntity<String> updateProduct(@RequestBody PackageEntity packageEntity) {
        packageService.update(packageEntity);
        return ResponseEntity.ok("Update successful");
    }
}
