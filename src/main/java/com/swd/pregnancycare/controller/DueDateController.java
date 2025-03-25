package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.*;
import com.swd.pregnancycare.enums.PregnancyPeriodEntity;
import com.swd.pregnancycare.enums.PregnancyTimeline;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.CalculatorServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/due-date-calculator")
@RestController
@Tag(name = "Due date Calculator", description = "APIs for calculating due dates and pregnancy stages")
@CrossOrigin
public class DueDateController {
    @Autowired
    private CalculatorServicesImp calculatorServicesImp;
    @Operation(summary = "Calculate due date from last period", description = "Calculates the estimated due date based on the last menstrual period and cycle length.")
    @PostMapping("/last-period")
    public ResponseEntity<?> getCaculateDueDate(@RequestBody LastPeriodInputDTO lastPeriodInput) {

        return ResponseEntity.ok(getResponse(calculatorServicesImp.getPeriodMethod(lastPeriodInput)));



    }
    @Operation(summary = "Calculate due date from conception date", description = "Estimates the due date based on the conception date and provides the pregnancy timeline.")
    @PostMapping("/conception-date")
    public ResponseEntity<?>  getPregnancyTimelineFromConception(@RequestBody ConceptionInputDTO input) {
        return ResponseEntity.ok(getResponse(calculatorServicesImp.getConceptionDate(input)));
    }
    @Operation(summary = "Calculate due date for IVF pregnancies", description = "Estimates the due date for IVF pregnancies based on embryo transfer day.")
    @PostMapping("/ivf")
    public ResponseEntity<?> getPregnancyTimelineFromIVF(@RequestBody IVFInputDTO input) {
        return ResponseEntity.ok(getResponse(calculatorServicesImp.getIVF(input)));
    }
    @Operation(summary = "Calculate due date from ultrasound", description = "Estimates the due date based on an ultrasound scan.")
    @PostMapping("/ultrasound")
    public ResponseEntity<?> getDueDateFromUltrasound(@RequestBody UltrasoundInputDTO ultrasoundInput) {

        return ResponseEntity.ok(getResponse(calculatorServicesImp.getUltraSound(ultrasoundInput)));
    }
    @Operation(summary = "Calculate pregnancy timeline from estimated due date", description = "Generates the pregnancy timeline based on an estimated due date.")
    @PostMapping("/from-due-date")
    public ResponseEntity<?> getTimelineFromDueDate(@RequestBody DueDateInputDTO dueDateInput) {
        if (dueDateInput.dueDate.isBefore(LocalDate.now().minusMonths(9)) || dueDateInput.dueDate.isAfter(LocalDate.now().plusMonths(12))) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(getResponse(dueDateInput));
    }
    public BaseResponse getResponse(Object data){
        BaseResponse response = new BaseResponse();
        response.setData(data);
        return response;
    }
}
