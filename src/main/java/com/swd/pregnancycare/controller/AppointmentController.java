package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.AppointmentDTO;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.AppointmentServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@Tag(name = "Appointment API", description = "API for managing appointments")
public class AppointmentController {
    @Autowired
    private AppointmentServicesImp appointmentServicesImp;
    @Operation(summary = "Create a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        BaseResponse response = new BaseResponse();
        appointmentServicesImp.makeAppointment(appointmentDTO);
        response.setMessage("Create appointment success");

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved appointments")
    })
    @GetMapping
    public ResponseEntity<?> getAllAppointments() {
        BaseResponse response = new BaseResponse();
        response.setData(appointmentServicesImp.getAllAppointment());
        response.setMessage("success");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get an appointment by fetus ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the appointment"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentByFetusId(@PathVariable int id) {
        BaseResponse response = new BaseResponse();
        response.setData(appointmentServicesImp.getAppointmentByFetusId(id));
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/event")
    public ResponseEntity<?> updateAppointment(@Parameter(description = "id Appointment",  example = "1") @RequestParam int id,
                                               @Parameter(description = "new event",  example = "hello world") @RequestParam String event) {
        appointmentServicesImp.updateAppointment(id,event);
        BaseResponse response = new BaseResponse();
        response.setMessage("Update success");
        return  ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer id) {
        appointmentServicesImp.deleteAppointment(id);
        BaseResponse response = new BaseResponse();
        response.setMessage("Delete Success");
        return  ResponseEntity.ok(response);
    }
}
