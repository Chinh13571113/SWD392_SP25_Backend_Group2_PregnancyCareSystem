package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.FetusRecodDTO;
import com.swd.pregnancycare.request.FetusRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.FetusServicesImp;
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
@Tag(name = "Fetus API", description = "Api for Fetus Insert")
@CrossOrigin
@RequestMapping(value = "/api/fetus-record")
@CrossOrigin
public class FetusRecordController {
    @Autowired
    private FetusServicesImp fetusServicesImp;



    @Operation(
            summary = "Get Fetus Record",
            description = "MEMBER can get their own fetus record by fetus id",
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
    @GetMapping("/findById")
    public ResponseEntity<?> getAllFetusRecord(@Parameter(description = "Fetus ID", required = true, example = "1") @RequestParam int fetusId){
        BaseResponse response = new BaseResponse();
        response.setData(fetusServicesImp.getFetusRecordById(fetusId));
        response.setCode(200);
        response.setMessage("");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Save Fetus Record",
            description = "MEMBER can create a new Fetus Record",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Save Fetus record successful",
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
    public ResponseEntity<?> saveFetusRecord(@Parameter(description = "fetus Id is required", example = "1") @RequestParam int id,
                                                @RequestBody FetusRecodDTO fetusRecordDto) {
        fetusServicesImp.saveFetusRecord(id,fetusRecordDto);
        BaseResponse response = new BaseResponse();
        response.setCode(201);
        response.setMessage("Successfully Saved fetus record.");
        return ResponseEntity.status(201).body(response);
    }

    // Operation to update a fetus


    // Operation to delete a fetus
    @Operation(
            summary = "Delete a Fetus Record",
            description = "User can delete a fetus by providing the fetus record ID",
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
    public ResponseEntity<?> deleteFetusRecord(@PathVariable int id) {
        fetusServicesImp.deleteFetusRecord(id);
        BaseResponse response = new BaseResponse();
        response.setMessage("Successfully deleted fetus with ID " + id);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Get a data of Fetus Record", description = "get data")
    @GetMapping("/Statistic/findById")
    public ResponseEntity<?> getStatisticFetusRecord(@Parameter(description = "Fetus ID", required = true, example = "1") @RequestParam int fetusId){
        BaseResponse response = new BaseResponse();
        response.setData(fetusServicesImp.getStatisticFetusRecordById(fetusId));
        response.setCode(200);
        response.setMessage("");
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Update Fetus Record", description = "Update Fetus Record")

    @PutMapping()
    public ResponseEntity<?> updateFetusRecord(@RequestBody FetusRecodDTO fetusRecodDTO){
        BaseResponse response = new BaseResponse();
        fetusServicesImp.updateFetusRecord(fetusRecodDTO);
        response.setMessage("Update Successfully");
        return ResponseEntity.ok(response);
    }
}
