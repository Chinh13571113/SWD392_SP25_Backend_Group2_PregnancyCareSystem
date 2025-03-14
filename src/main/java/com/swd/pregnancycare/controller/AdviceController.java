package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.request.FetusRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.AdviceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/advices")
@CrossOrigin
@Tag(name = "Advice API", description = "API for Advices")
public class AdviceController {
  @Autowired
  private AdviceServiceImpl adviceServiceImpl;

  @Operation(
          summary = "Create a new advice",
          description = "Member can create a new advice",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Created advice successful",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Invalid input provided"
                  )
          }
  )
  @PostMapping()
  public ResponseEntity<?> createAdvice(@RequestParam int id,
                                        @RequestParam String title,
                                        @RequestParam String description) {
    adviceServiceImpl.saveAdvice(id, title, description);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created advice successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Get all advices",
          description = "Member can get all advices",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got advice list successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "got advice list successfully",
  "data": [
    {
      "id": 1,
      "title": "Something",
      "description": "Something",
      "status": "false",
      "answer": "answered",
      "fetus": {
        "id": 2,
        "name": "Baby B",
        "dueDate": "2025-08-15",
        "gender": "girl"
      }
    },
    {
      "id": 2,
      "title": "Something 2",
      "description": "Something 2",
      "status": "true",
      "answer": "answered 2",
      "fetus": {
        "id": 3,
        "name": "Baby A",
        "dueDate": "2025-08-15",
        "gender": "boy"
      }
    }
  ]
}
"""
                                  )
                          )
                  )
          }
  )

  @GetMapping()
  public ResponseEntity<?> getAllAdvices(){
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("got advice list successfully");
    response.setData(adviceServiceImpl.getAllAdvices());
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Delete advice",
          description = "Expert delete advice",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted advice successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "Deleted advice successfully"
}
"""
                                  )
                          )
                  )
          }
  )

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAdvice(@PathVariable int id){
    BaseResponse response = new BaseResponse();
    adviceServiceImpl.deleteAdvice(id);
    response.setCode(200);
    response.setMessage("Deleted advice successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
                  summary = "Update advice",
                  description = "Expert update advice",
                  responses = {
                          @ApiResponse(
                          responseCode = "200",
                          description = "Updated advice successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "Updated advice successfully"
}
"""
                                  )
                          )
                  )
          }
  )

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAdvice(@PathVariable int id, @RequestParam String answer, @RequestParam boolean status){
    BaseResponse response = new BaseResponse();
    adviceServiceImpl.updateAdvice(id, answer, status);
    response.setCode(200);
    response.setMessage("Updated advice successfully");
    return ResponseEntity.ok(response);
  }
}
