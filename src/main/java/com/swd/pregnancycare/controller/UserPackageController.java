package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.entity.UserPackageEntity;
import com.swd.pregnancycare.services.UserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/userpackage")
@RestController
@CrossOrigin
public class UserPackageController {
    @Autowired
    private UserPackageService userpackageService ;

    @Autowired
    public UserPackageController(UserPackageService userpackageService){
        this.userpackageService =userpackageService ;
    }

    @PostMapping
    public ResponseEntity addUserPackage(@RequestBody UserPackageEntity userpackageEntity) {
        userpackageService.addNew(userpackageEntity);
        return ResponseEntity.ok(" Create UserPackage successfully ");
    }

    @PutMapping("api/userpackage/{index}")
    public ResponseEntity updateUserPackageEntity(
            @PathVariable int index,
            @RequestBody UserPackageEntity userPackageEntity
    ) {
        try {
            userpackageService.update(userPackageEntity);
            return ResponseEntity.ok("Update UserPackage successfully");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }

    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteUserPackageEntity(@PathVariable int id) {
        try {
            userpackageService.delete(id);
            return ResponseEntity.ok().body("Delete UserPackage successfully");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
    }


}
