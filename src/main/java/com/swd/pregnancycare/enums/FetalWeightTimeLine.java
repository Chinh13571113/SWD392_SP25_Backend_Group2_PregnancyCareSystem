package com.swd.pregnancycare.enums;

import java.util.List;

public class FetalWeightTimeLine {private static final List<FetalWeightEntity> fetalWeightList = List.of(
        new FetalWeightEntity(12, 10, 15, 20, "g"),
        new FetalWeightEntity(13, 15, 25, 35, "g"),
        new FetalWeightEntity(14, 25, 40, 55, "g"),
        new FetalWeightEntity(15, 35, 60, 80, "g"),
        new FetalWeightEntity(16, 50, 85, 110, "g"),
        new FetalWeightEntity(17, 70, 120, 150, "g"),
        new FetalWeightEntity(18, 90, 160, 200, "g"),
        new FetalWeightEntity(19, 125, 225, 275, "g"),
        new FetalWeightEntity(20, 160, 280, 340, "g"),
        new FetalWeightEntity(21, 200, 350, 420, "g"),
        new FetalWeightEntity(22, 250, 430, 510, "g"),
        new FetalWeightEntity(23, 300, 500, 600, "g"),
        new FetalWeightEntity(24, 400, 600, 720, "g"),
        new FetalWeightEntity(25, 500, 700, 850, "g"),
        new FetalWeightEntity(26, 600, 800, 1000, "g"),
        new FetalWeightEntity(27, 750, 1000, 1250, "g"),
        new FetalWeightEntity(28, 900, 1150, 1400, "g"),
        new FetalWeightEntity(29, 1050, 1300, 1600, "g"),
        new FetalWeightEntity(30, 1200, 1450, 1750, "g"),
        new FetalWeightEntity(31, 1350, 1600, 1950, "g"),
        new FetalWeightEntity(32, 1500, 1800, 2150, "g"),
        new FetalWeightEntity(33, 1700, 2000, 2400, "g"),
        new FetalWeightEntity(34, 1900, 2200, 2600, "g"),
        new FetalWeightEntity(35, 2100, 2400, 2800, "g"),
        new FetalWeightEntity(36, 2300, 2600, 3000, "g"),
        new FetalWeightEntity(37, 2500, 2800, 3200, "g"),
        new FetalWeightEntity(38, 2700, 3000, 3400, "g"),
        new FetalWeightEntity(39, 2900, 3200, 3600, "g"),
        new FetalWeightEntity(40, 3000, 3400, 3800, "g")
);

    public static List<FetalWeightEntity> getAllFetalWeights() {
        return fetalWeightList;
    }

    public static FetalWeightEntity getFetalWeightByWeek(int week) {
        return fetalWeightList.stream()
                .filter(fw -> fw.getWeek() == week)
                .findFirst()
                .orElse(null);
    }
}
