package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.request.UserEditRequest;
import com.swd.pregnancycare.request.UserRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.AdminServicesImp;
import com.swd.pregnancycare.services.UserServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Admin")
@Tag(name = "Admin ", description = "API for Admin")
public class AdminController {
   @Autowired
   private AdminServicesImp adminServices;
    @Autowired
    private UserServicesImp userServicesImp;

    @Operation(summary = "get DashBoard", description = "Removes a specific user package by ID")
    @GetMapping("/DashBoard")
    public ResponseEntity<?> getDashBoard(){
        BaseResponse response = new BaseResponse();
        response.setData(adminServices.getDashboard());
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Admin can provide account", description = "Removes a specific user package by ID")
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest){
        adminServices.addUser(userRequest);
        BaseResponse response = new BaseResponse();
        response.setMessage("Add Success");
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Admin can edit account", description = "Removes a specific user package by ID")
    @PutMapping("/updateUser")
    public ResponseEntity<?> editUser(@RequestBody UserEditRequest userEditRequest){
        adminServices.editUser(userEditRequest);
        BaseResponse response = new BaseResponse();
        response.setMessage("Edit success");
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Admin can edit account", description = "Removes a specific user package by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        userServicesImp.deleteUserById(id);
        BaseResponse response = new BaseResponse();
        response.setMessage("Delete success");
        return ResponseEntity.ok(response);
    }
}
