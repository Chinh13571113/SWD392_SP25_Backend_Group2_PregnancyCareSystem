package com.swd.pregnancycare.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment/schedule")
@Tag(name = "Appointment API", description = "API for managing reminders related to appointments")
public class ScheduleController {
    // 1️⃣ Tạo lời nhắc
    @Operation(summary = "Create a new reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder created successfully")
    })
    @PostMapping
    public ResponseEntity<?> createReminder() {
        return ResponseEntity.ok("Reminder created successfully.");
    }

    // 2️⃣ Lấy danh sách lời nhắc theo lịch hẹn
    @Operation(summary = "Get all reminders for an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reminders")
    })
    @GetMapping
    public ResponseEntity<?> getRemindersByAppointment(@RequestParam Integer appointmentId) {
        return ResponseEntity.ok("Retrieved reminders for appointment ID: " + appointmentId);
    }

    // 3️⃣ Cập nhật nội dung hoặc ngày của lời nhắc
    @Operation(summary = "Update reminder details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder updated successfully")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable Integer id) {
        return ResponseEntity.ok("Reminder with ID " + id + " updated successfully.");
    }

    // 4️⃣ Xóa lời nhắc
    @Operation(summary = "Delete a reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable Integer id) {
        return ResponseEntity.ok("Reminder with ID " + id + " deleted successfully.");
    }

    // 5️⃣ Gửi email nhắc nhở (Scheduler chạy hàng ngày)
    @Operation(summary = "Send reminder emails (Scheduled task)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emails sent successfully")
    })
    @GetMapping("/send-emails")
    public ResponseEntity<?> sendReminderEmails() {
        return ResponseEntity.ok("Reminder emails sent successfully.");
    }
}
