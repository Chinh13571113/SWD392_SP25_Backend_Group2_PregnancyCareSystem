package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.GroupServicesImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/groups")
@CrossOrigin
@Tag(name = "Group API", description = "API for Groups")
public class GroupController {
  @Autowired
  private GroupServicesImpl groupServicesImpl;

  @Operation(
          summary = "Create Group",
          description = "MEMBER can create group",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Create a new group",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Created group successfully\",\n  \"data\": \"[{}, {}]\"\n}"
                                  )
                          )
                  ),
          }

  )
  @PostMapping
  public ResponseEntity<?> saveGroup(@RequestBody GroupRequest group) {
    groupServicesImpl.saveGroup(group);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created group successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Get all groups",
          description = "MEMBER or ADMIN can get all groups",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Got all groups successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Got all groups successfully\",\n  \"data\": \"[{}, {}]\"\n}"
                                  )
                          )
                  ),
          }

  )
  @GetMapping
  public ResponseEntity<?> getAllBlogs() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setData(groupServicesImpl.getAllGroups());
    response.setMessage("Got all groups successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Delete a group",
          description = "MEMBER or ADMIN can delete a group",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted group successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Deleted group successfully\"\n}"
                                  )
                          )
                  )
          }
  )
  @DeleteMapping("/{id}")
  public  ResponseEntity<?> deleteGroup(@PathVariable int id) {
    groupServicesImpl.deleteGroup(id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Deleted group successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Update a group",
          description = "ADMIN or MEMBER can update a group",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Updated group successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Updated group successfully\"\n}"
                                  )
                          )
                  ),
          }

  )
  @PutMapping("/{id}")
  public ResponseEntity<?> updateGroup(@PathVariable int id,
                                       @RequestParam String name,
                                       @RequestParam String description) {
    groupServicesImpl.updateGroup(name, description, id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Updated group successfully");
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Register user to group",
          description = "MEMBER can register to a group",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "User registered successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          value = "{ \"code\": 200, \"message\": \"User registered to group successfully\" }"
                                  )
                          )
                  )
          }
  )
  @PostMapping("/{groupId}/members")
  public ResponseEntity<?> addMemberToGroup(@PathVariable int groupId,
                                       @RequestParam String email) {
    groupServicesImpl.addMemberToGroup(groupId, email);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("User registered to group successfully");
    return ResponseEntity.ok(response);
  }
}
