package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.request.FetusRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.FetusServicesImp;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Fetus API", description = "Api for Fetus Insert")
@RequestMapping(value = "/api/fetus")
public class FetusController {
    @Autowired
    private FetusServicesImp fetusServicesImp;
    @Operation(
            summary = "Get all List Fetus",
            description = "User can get all List Fetus",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get list successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = """
    {
      "code": 200,
      "message": "Lấy danh sách thai nhi thành công",
      "data": [
        {
          "id": 1,
          "name": "Baby A",
          "dueDate": "2025-07-10",
          "gender": "Male",
          "user": {
            "id": 1,
            "email": "user@example.com"
          }
        },
        {
          "id": 2,
          "name": "Baby B",
          "dueDate": "2025-08-15",
          "gender": "Female",
          "user": {
            "id": 2,
            "email": "user2@example.com"
          }
        }
      ]
    }
    """
                                    )
                            )
                    ),


            }

    )
    @GetMapping()
    public ResponseEntity<?> getAllFetus(){
        BaseResponse response = new BaseResponse();
        response.setData(fetusServicesImp.getAllFetus());
        response.setCode(200);
        response.setMessage("");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get My list Fetus",
            description = "User can get all List Fetus",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get list successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = """
    {
      "code": 200,
      "message": "Lấy danh sách thai nhi thành công",
      "data": [
        {
          "id": 1,
          "name": "Baby A",
          "dueDate": "2025-07-10",
          "gender": "Male",
          "user": {
            "id": 1,
            "email": "user@example.com"
          }
        },
        {
          "id": 2,
          "name": "Baby B",
          "dueDate": "2025-08-15",
          "gender": "Female",
          "user": {
            "id": 2,
            "email": "user2@example.com"
          }
        }
      ]
    }
    """
                                    )
                            )
                    ),


            }

    )
    @GetMapping("/MyFetus")
    public ResponseEntity<?> getMyFetus(){
        BaseResponse response = new BaseResponse();
        response.setData(fetusServicesImp.getMyFetus());
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create a new Fetus",
            description = "User can create a new Fetus",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Create fetus successful",
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
    public ResponseEntity<?> createFetus(@RequestBody FetusRequest fetusRequest) {
        FetusDTO fetusDTO = fetusServicesImp.saveFetus(fetusRequest);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Successfully created fetus.");
        response.setData(fetusDTO);
        return ResponseEntity.status(200).body(response);
    }

    // Operation to update a fetus
    @Operation(
            summary = "Update an existing Fetus",
            description = "User can update an existing Fetus by providing the fetus ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update fetus successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Fetus not found"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFetus(@RequestBody FetusRequest fetusRequest, @PathVariable int id) {
        fetusServicesImp.updateFetus(fetusRequest, id);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Successfully updated fetus with ID " + id);
        return ResponseEntity.ok(response);
    }

    // Operation to delete a fetus
    @Operation(
            summary = "Delete an existing Fetus",
            description = "User can delete a fetus by providing the fetus ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete fetus successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Fetus not found"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFetus(@PathVariable int id) {
        fetusServicesImp.deleteFetus(id);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Successfully deleted fetus with ID " + id);
        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "Get Fetus week",
            description = "User can get Fetus week",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get list successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = """
    {
      "code": 200,
      "message": "Lấy danh sách thai nhi thành công",
      "data": [
        {
          "id": 1,
          "name": "Baby A",
          "dueDate": "2025-07-10",
          "gender": "Male",
          "user": {
            "id": 1,
            "email": "user@example.com"
          }
        },
        {
          "id": 2,
          "name": "Baby B",
          "dueDate": "2025-08-15",
          "gender": "Female",
          "user": {
            "id": 2,
            "email": "user2@example.com"
          }
        }
      ]
    }
    """
                                    )
                            )
                    ),


            }

    )
    @GetMapping("/My-Fetus-Week/{id}")
    public ResponseEntity<?> getFetusWeek(@PathVariable int id){
        BaseResponse response = new BaseResponse();
        response.setData(fetusServicesImp.getFetusWeek(id));
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
}
