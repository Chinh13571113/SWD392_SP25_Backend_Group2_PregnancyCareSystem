package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.GroupServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/groups")
@CrossOrigin
@Tag(name = "Group API", description = "API for Groups")
@SecurityRequirement(name = "bearerAuth")
public class GroupController {
  @Autowired
  private GroupServices groupServices;

  @Operation(
          summary = "Save Group",
          description = "Allow members to create group",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Save group",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Saved group successfully\",\n  \"data\": {\n \"id\": 1, \n \"name\": \"Pregnancy\", \n \"description\": \"something\", \n \"date\": ,\n \"}\"\n}"
                                  )
                          )
                  ),
          }

  )
  @PostMapping
  public ResponseEntity<?> saveGroup(@RequestBody GroupRequest group) {
    BaseResponse baseResponse = groupServices.saveGroup(group);
    return ResponseEntity.ok(baseResponse);
  }
}
