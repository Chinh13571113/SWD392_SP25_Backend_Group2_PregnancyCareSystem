package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.AppointmentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@Tag(name = "Appointment API", description = "API for managing appointments")
public class AppointmentController {
    @Operation(summary = "Create a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved appointments")
    })
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get an appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the appointment"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Integer id) {

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO appointmentDTO) {

        return  ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer id) {

        return  ResponseEntity.ok().build();
    }
}
