package com.swd.pregnancycare.controller;


import com.swd.pregnancycare.enums.*;
import com.swd.pregnancycare.services.FetalGrowthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fetal-growth")
@CrossOrigin
public class FetalGrowthController {
    private final FetalGrowthService fetalGrowthService;

    public FetalGrowthController(FetalGrowthService fetalGrowthService) {
        this.fetalGrowthService = fetalGrowthService;
    }

    // 1️⃣ Lấy chu vi vòng đầu theo tuần
    @GetMapping("/head-circumference/{week}")
    public HeadCircumferenceEntity getHeadCircumference(@PathVariable int week) {
        return HeadCircumferenceTimeLine.getHeadCircumferenceByWeek(week);
    }

    // 2️⃣ Lấy chiều cao thai nhi theo tuần
    @GetMapping("/length/{week}")
    public FetalHeightEntity getFetalHeight(@PathVariable int week) {
        return FetalHeightTimeLine.getFetalHeightByWeek (week);
    }

    // 3️⃣ Lấy cân nặng thai nhi theo tuần
    @GetMapping("/weight/{week}")
    public FetalWeightEntity getFetalWeight(@PathVariable int week) {
        return FetalWeightTimeLine.getFetalWeightByWeek(week);
    }

    // 4️⃣ So sánh dữ liệu thai nhi nhập vào với bảng chuẩn
    @PostMapping("/compare")
    public FetalComparisonResult compareFetalData(@RequestParam int week,
                                                  @RequestParam double headCircumference,
                                                  @RequestParam double fetalLength,
                                                  @RequestParam double fetalWeight) {
        return fetalGrowthService.compareFetalData(week, headCircumference, fetalLength, fetalWeight);
    }
}
