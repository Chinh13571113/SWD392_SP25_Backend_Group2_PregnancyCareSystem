package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.request.AdviceRequest;
import com.swd.pregnancycare.request.FetusRequest;
import com.swd.pregnancycare.response.AdviceResponse;
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


  // API for MEMBER
  @Operation(
          summary = "Create a new advice",
          description = "MEMBER can create a new advice",
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
  public ResponseEntity<?> createAdvice(@RequestBody AdviceRequest adviceRequest) {
    adviceServiceImpl.saveAdvice(adviceRequest);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created advice successfully");
    return ResponseEntity.ok(response);
  }


  @Operation(
          summary = "Get all advices for member",
          description = "MEMBER can get all their advices",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got my advice list successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = AdviceResponse.class)
                          )
                  )
          }
  )
  @GetMapping("/members")
  public ResponseEntity<?> getAllAdvicesForMember(){
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("got my advice list successfully");
    response.setData(adviceServiceImpl.getAllAdvicesForMember());
    return ResponseEntity.ok(response);
  }



  // API for EXPERT
  @Operation(
          summary = "Get all advices for experts",
          description = "EXPERT can get all advices",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got advice list successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = AdviceResponse.class)
                          )
                  )
          }
  )
  @GetMapping()
  public ResponseEntity<?> getAllAdvicesForExpert(){
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("got advice list successfully");
    response.setData(adviceServiceImpl.getAllAdvicesForExpert());
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
                                  schema = @Schema(implementation = BaseResponse.class)
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
                  summary = "Answer advice",
                  description = "EXPERT answer advice",
                  responses = {
                          @ApiResponse(
                          responseCode = "200",
                          description = "Answered advice successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  )
          }
  )

  @PutMapping("/status/{adviceId}")
  public ResponseEntity<?> answerAdvice(@PathVariable int adviceId, @RequestParam String answer){
    BaseResponse response = new BaseResponse();
    adviceServiceImpl.answerAdvice(adviceId, answer);
    response.setCode(200);
    response.setMessage("Answered advice successfully");
    return ResponseEntity.ok(response);
  }
}
