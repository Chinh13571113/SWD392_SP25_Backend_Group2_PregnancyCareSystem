package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/packages")
@CrossOrigin
@RestController
public class PackageController {
    @Autowired
    private PackageService packageService ;



    @PostMapping
    public ResponseEntity addPackage(@RequestBody PackageEntity packageEntity) {
        packageService.addNew(packageEntity);
        return ResponseEntity.ok(" Create Package successfully ");
    }
    @GetMapping
    public ResponseEntity getPackages() {
        var packageList = packageService.getAll();
        return ResponseEntity.ok(packageList);
    }
    @GetMapping("{id}")
    public ResponseEntity getPackagesByID(@PathVariable int id) {
        var packages = packageService.getbyId(id);
        return ResponseEntity.ok(packages);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deletePackage(@PathVariable int id) {
        try {
            packageService.deleteById(id);
            return ResponseEntity.ok().body("Delete Package successfully");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Package not Found");
        }
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody PackageEntity packageEntity) {

        packageService.update(packageEntity);
        return ResponseEntity.ok("Update ok !!!");
    }
}