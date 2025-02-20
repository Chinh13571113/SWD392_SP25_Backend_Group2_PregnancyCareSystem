package com.swd.pregnancycare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {
    @GetMapping
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("hello");
    }
}
