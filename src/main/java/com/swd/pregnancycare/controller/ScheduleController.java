package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.ScheduleDTO;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.ScheduleServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment/schedule")
@Tag(name = "Appointment API", description = "API for managing reminders related to appointments")
public class ScheduleController {
    @Autowired
    private ScheduleServicesImp scheduleServicesImp;
    // 1️⃣ Tạo lời nhắc
    @Operation(summary = "Create a new reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder created successfully")
    })
    @PostMapping
    public ResponseEntity<?> createReminder(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleServicesImp.createReminder(scheduleDTO);
        BaseResponse response = new BaseResponse();
        response.setMessage("Create reminder success");
        return ResponseEntity.ok(response);
    }

    // 2️⃣ Lấy danh sách lời nhắc theo lịch hẹn
    @Operation(summary = "Get all reminders for an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reminders")
    })
    @GetMapping
    public ResponseEntity<?> getRemindersByAppointment(@RequestParam Integer appointmentId) {
        BaseResponse response = new BaseResponse();
        response.setData(scheduleServicesImp.getReminderByAppointmentId(appointmentId));
        response.setMessage("Get reminder successfully");
        return ResponseEntity.ok(response);

    }

    // 3️⃣ Cập nhật nội dung hoặc ngày của lời nhắc
    @Operation(summary = "Update reminder details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder updated successfully")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable Integer id, @RequestBody ScheduleDTO scheduleDTO) {
        scheduleServicesImp.updateReminder(id,scheduleDTO);
        BaseResponse response =new BaseResponse();
        response.setMessage("Update Success");
        return ResponseEntity.ok(response);
    }

    // 4️⃣ Xóa lời nhắc
    @Operation(summary = "Delete a reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable Integer id) {
        scheduleServicesImp.deleteReminder(id);
        BaseResponse response = new BaseResponse();
        response.setMessage("Delete success");
        return ResponseEntity.ok(response);
    }

    // 5️⃣ Gửi email nhắc nhở (Scheduler chạy hàng ngày)
    @Operation(summary = "Send reminder emails (Scheduled task)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emails sent successfully")
    })
    @GetMapping("/send-emails")
    public ResponseEntity<?> sendReminderEmails() throws MessagingException {
        scheduleServicesImp.sendReminderEmail();
        BaseResponse response =new BaseResponse();
        response.setMessage("Send Mail success");
        return ResponseEntity.ok(response);
    }
}
