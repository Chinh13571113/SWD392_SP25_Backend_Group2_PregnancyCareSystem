package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.request.PackageRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/packages")
@CrossOrigin
@RestController
@Tag(name = "Package Management", description = "APIs for managing pregnancy care packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Operation(summary = "Create a new package", description = "Adds a new package to the system")
    @PostMapping
    public ResponseEntity<?> addPackage(@RequestBody PackageRequest request) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Created a package successfully");
        response.setData(packageService.addNew(request));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all packages", description = "Retrieves a list of all available packages")
    @GetMapping
    public ResponseEntity<?> getPackages() {
        var packageList = packageService.getAll();
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Got all packages successfully");
        response.setData(packageList);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a package by ID", description = "Retrieves a specific package by its ID")
    @GetMapping("{id}")
    public ResponseEntity<?> getPackagesByID(@PathVariable int id) {
        var packages = packageService.getbyId(id);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Got a package successfully");
        response.setData(packages);
        return ResponseEntity.ok(packages);
    }

    @Operation(summary = "Delete a package", description = "Deletes a package by its ID")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePackage(@PathVariable int id) {
        packageService.deleteById(id);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Deleted Package successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a package", description = "Updates an existing package")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody PackageRequest request) {
        packageService.update(id, request);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Updated Package successfully");
        return ResponseEntity.ok(response);
    }
}
