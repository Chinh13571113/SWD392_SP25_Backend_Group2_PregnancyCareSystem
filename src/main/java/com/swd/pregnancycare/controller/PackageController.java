package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.request.PackageRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.PackageServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/packages")
@CrossOrigin
@Tag(name = "Package API", description = "API for Packages")
public class PackageController {

  @Autowired
  private PackageServiceImp packageServiceImp;

  @Operation(
          summary = "Create a new package",
          description = "ADMIN can create a package",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)

                          )
                  ),
          }

  )
  @PostMapping
  public ResponseEntity<?> createPackage(@RequestBody PackageRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", packageServiceImp.createPackage(request));
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Get all packages",
          description = "ADMIN can get all packages",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)

                          )
                  ),
          }

  )
  @GetMapping
  public ResponseEntity<?> getAllPackages() {
    BaseResponse response =
            new BaseResponse(200, "success", packageServiceImp.getAllPackages());
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Get package by ID",
          description = "ADMIN can get package by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)

                          )
                  ),
          }

  )
  @GetMapping("/{id}")
  public ResponseEntity<?> getPackageById(@PathVariable int id) {
    BaseResponse response =
            new BaseResponse(200, "success", packageServiceImp.getPackageById(id));
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Update package by ID",
          description = "ADMIN can update package by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)

                          )
                  ),
          }

  )
  @PutMapping("/{id}")
  public ResponseEntity<?> updatePackage(@PathVariable int id, @RequestBody PackageRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", packageServiceImp.updatePackage(id, request));
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Delete package by ID",
          description = "ADMIN can delete package by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)

                          )
                  ),
          }

  )
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePackage(@PathVariable int id) {
    BaseResponse response =
            new BaseResponse(200, "success");
    packageServiceImp.deletePackage(id);
    return ResponseEntity.ok(response);
  }
}
