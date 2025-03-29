package com.swd.pregnancycare.services;

import com.swd.pregnancycare.enums.*;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.swd.pregnancycare.enums.FetalHeightTimeLine.getFetalHeightByWeek;

@Service
public class FetalGrowthService {

    private final List<HeadCircumferenceEntity> headCircumferenceList = List.of(
            new HeadCircumferenceEntity(12, 65, 70, 80, "mm"),
            new HeadCircumferenceEntity(13, 75, 80, 90, "mm"),
            new HeadCircumferenceEntity(14, 85, 90, 100, "mm"),
            new HeadCircumferenceEntity(40, 300, 340, 380, "mm")
    );

    private final List<FetalHeightEntity> fetalLengthList = List.of(
            new FetalHeightEntity(12, 50, 55, 60, "mm"),
            new FetalHeightEntity(13, 60, 65, 70, "mm"),
            new FetalHeightEntity(14, 75, 80, 90, "mm"),
            new FetalHeightEntity(40, 480, 500, 520, "mm")
    );

    private final List<FetalWeightEntity> fetalWeightList = List.of(
            new FetalWeightEntity(12, 10, 15, 20, "g"),
            new FetalWeightEntity(13, 15, 25, 35, "g"),
            new FetalWeightEntity(14, 25, 40, 55, "g"),
            new FetalWeightEntity(40, 3000, 3400, 3800, "g")
    );

    public FetalComparisonResult compareFetalData(int week, Double userHeadCircumference, Double userFetalLength, Double userFetalWeight) {
        HeadCircumferenceEntity hc = HeadCircumferenceTimeLine.getHeadCircumferenceByWeek(week);
        FetalHeightEntity fl = FetalHeightTimeLine.getFetalHeightByWeek(week);
        FetalWeightEntity fw = FetalWeightTimeLine.getFetalWeightByWeek(week);

        if (hc == null || fl == null || fw == null) {
            return new FetalComparisonResult(week, "Data not available", "Data not available", "Data not available");
        }

        String headComparison = (userHeadCircumference == null) ? "No comparison" : compareValue(userHeadCircumference, hc.getLowerBound(), hc.getUpperBound());
        String lengthComparison = (userFetalLength == null) ? "No comparison" : compareValue(userFetalLength, fl.getLowerBound(), fl.getUpperBound());
        String weightComparison = (userFetalWeight == null) ? "No comparison" : compareValue(userFetalWeight, fw.getLowerBound(), fw.getUpperBound());

        return new FetalComparisonResult(week, headComparison, lengthComparison, weightComparison);
    }

    private String compareValue(double userValue, double lowerBound, double upperBound) {
        if (userValue < lowerBound) return "Below standard";
        if (userValue > upperBound) return "Above standard";
        return "Within standard";
    }

    public HeadCircumferenceEntity getHeadCircumferenceByWeek(int week) {
        return headCircumferenceList.stream()
                .filter(hc -> hc.getWeek() == week)
                .findFirst()
                .orElse(null);
    }

    public FetalHeightEntity getFetalLengthByWeek(int week) {
        return fetalLengthList.stream()
                .filter(fl -> fl.getWeek() == week)
                .findFirst()
                .orElse(null);
    }

    public FetalWeightEntity getFetalWeightByWeek(int week) {
        return fetalWeightList.stream()
                .filter(fw -> fw.getWeek() == week)
                .findFirst()
                .orElse(null);
    }
}
