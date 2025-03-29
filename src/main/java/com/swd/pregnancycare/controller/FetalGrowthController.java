package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.enums.*;
import com.swd.pregnancycare.services.FetalGrowthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fetal-growth")
@CrossOrigin
@Tag(name = "Fetus API", description = "API for monitoring fetal growth")
public class FetalGrowthController {
    private final FetalGrowthService fetalGrowthService;

    public FetalGrowthController(FetalGrowthService fetalGrowthService) {
        this.fetalGrowthService = fetalGrowthService;
    }

    // 1️⃣ Get head circumference by week
    @Operation(summary = "Get head circumference by week", description = "Returns the standard fetal head circumference for the given gestational week.")
    @GetMapping("/head-circumference/{week}")
    public HeadCircumferenceEntity getHeadCircumference(@PathVariable int week) {
        return HeadCircumferenceTimeLine.getHeadCircumferenceByWeek(week);
    }
    //

    // 2️⃣ Get fetal length by week
    @Operation(summary = "Get fetal length by week", description = "Returns the standard fetal length for the given gestational week.")
    @GetMapping("/length/{week}")
    public FetalHeightEntity getFetalHeight(@PathVariable int week) {
        return FetalHeightTimeLine.getFetalHeightByWeek(week);
    }

    // 3️⃣ Get fetal weight by week
    @Operation(summary = "Get fetal weight by week", description = "Returns the standard fetal weight for the given gestational week.")
    @GetMapping("/weight/{week}")
    public FetalWeightEntity getFetalWeight(@PathVariable int week) {
        return FetalWeightTimeLine.getFetalWeightByWeek(week);
    }

    // 4️⃣ Compare fetal data with standard chart
    @Operation(summary = "Compare fetal data", description = "Compares the entered fetal data with the standard chart to evaluate fetal growth.")
    @PostMapping("/compare")
    public FetalComparisonResult compareFetalData(@RequestParam int week,
                                                  @RequestParam double headCircumference,
                                                  @RequestParam double fetalLength,
                                                  @RequestParam double fetalWeight) {
        return fetalGrowthService.compareFetalData(week, headCircumference, fetalLength, fetalWeight);
    }
}
