package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.entity.UserPackageEntity;
import com.swd.pregnancycare.response.BaseResponse;
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
    public ResponseEntity<?> addUserPackage(@RequestParam int packageId) {
        userPackageService.addNew(packageId);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Created UserPackage successfully");
        return ResponseEntity.ok(response);
    }



}
