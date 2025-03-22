package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.*;
import com.swd.pregnancycare.enums.PregnancyPeriodEntity;
import com.swd.pregnancycare.enums.PregnancyTimeline;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/duedate")
@RestController
@CrossOrigin
public class DueDateController {


    @PostMapping("/last-period")
    public ResponseEntity getCaculateDueDate(@RequestBody LastPeriodInputDTO lastPeriodInput) {
        PregnancyTimeline pregnancyTimeline = new PregnancyTimeline();

        if (lastPeriodInput.getCycleLength() < 20 || lastPeriodInput.getCycleLength() >45) {
            return ResponseEntity.badRequest().body("Invalid cycle length. Must be between 20 and 45 days.");
        }
        pregnancyTimeline.updateTimelineWithDates(lastPeriodInput.getLastPeriod(), lastPeriodInput.getCycleLength());
        return ResponseEntity.ok(pregnancyTimeline);



    }

    @PostMapping("/conception-date")
    public ResponseEntity  getPregnancyTimelineFromConception(@RequestBody ConceptionInputDTO input) {
        if (input.getConceptionDate() == null) {
            return ResponseEntity.badRequest().body(null);
        }


        LocalDate dueDate = input.getConceptionDate().plusDays(266).minusWeeks(2);


        PregnancyTimeline pregnancyTimeline = new PregnancyTimeline();
        List<PregnancyPeriodEntity> updatedTimeline = new ArrayList<>();

        for (PregnancyPeriodEntity event : pregnancyTimeline) {

            LocalDate eventDate = input.getConceptionDate().plusWeeks(event.getStartWeek()-2);
            updatedTimeline.add(new PregnancyPeriodEntity(
                    event.getStartWeek(),
                    event.getEndWeek(),
                    event.getDecription(),
                    event.getTrimester(),
                    eventDate
            ));
        }

        return ResponseEntity.ok(updatedTimeline);
    }

    @PostMapping("/ivf")
    public ResponseEntity getPregnancyTimelineFromIVF(@RequestBody IVFInputDTO input) {
        if (input.getConceptionDate() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (input.getIvdDate() != 3 &&input.getIvdDate() != 5) {
            return ResponseEntity.badRequest().body("ivf day must be 3 or 5 days");
        }


        LocalDate dueDate = input.getConceptionDate().plusDays(266).minusWeeks(2);


        PregnancyTimeline pregnancyTimeline = new PregnancyTimeline();
        List<PregnancyPeriodEntity> updatedTimeline = new ArrayList<>();

        for (PregnancyPeriodEntity event : pregnancyTimeline) {

            LocalDate eventDate = input.getConceptionDate().plusWeeks(event.getStartWeek()-2).minusDays(input.getIvdDate());
            updatedTimeline.add(new PregnancyPeriodEntity(
                    event.getStartWeek(),
                    event.getEndWeek(),
                    event.getDecription(),
                    event.getTrimester(),
                    eventDate
            ));
        }

        return ResponseEntity.ok(updatedTimeline);
    }

    @PostMapping("/ultrasound")
    public ResponseEntity<List<PregnancyPeriodEntity>> getDueDateFromUltrasound(@RequestBody UltrasoundInputDTO ultrasoundInput) {
        if (ultrasoundInput.gestationalWeeks < 4 || ultrasoundInput.gestationalWeeks > 42) {
            return ResponseEntity.badRequest().body(null);
        }

        LocalDate dueDate = PregnancyTimeline.getDueDateFromUltrasound(
                ultrasoundInput.ultrasoundDate,
                ultrasoundInput.gestationalWeeks,
                ultrasoundInput.gestationalDays
        );

        PregnancyTimeline pregnancyTimeline = new PregnancyTimeline();
        pregnancyTimeline.updateTimelineWithDueDate(dueDate);
        return ResponseEntity.ok(pregnancyTimeline);
    }
    @PostMapping("/from-due-date")
    public ResponseEntity<List<PregnancyPeriodEntity>> getTimelineFromDueDate(@RequestBody DueDateInputDTO dueDateInput) {
        if (dueDateInput.dueDate.isBefore(LocalDate.now().minusMonths(9)) || dueDateInput.dueDate.isAfter(LocalDate.now().plusMonths(12))) {
            return ResponseEntity.badRequest().body(null);
        }

        PregnancyTimeline pregnancyTimeline = new PregnancyTimeline();
        pregnancyTimeline.updateTimelineWithDueDate(dueDateInput.dueDate);
        return ResponseEntity.ok(pregnancyTimeline);
    }
}
