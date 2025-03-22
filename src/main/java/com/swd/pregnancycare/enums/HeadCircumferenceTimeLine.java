package com.swd.pregnancycare.enums;


import java.util.List;


public class HeadCircumferenceTimeLine {

    private static final List<HeadCircumferenceEntity> headCircumferenceList = List.of(
            new HeadCircumferenceEntity(12, 65, 70, 80, "mm"),
            new HeadCircumferenceEntity(13, 75, 80, 90, "mm"),
            new HeadCircumferenceEntity(14, 85, 90, 100, "mm"),
            new HeadCircumferenceEntity(15, 90, 100, 110, "mm"),
            new HeadCircumferenceEntity(16, 95, 110, 125, "mm"),
            new HeadCircumferenceEntity(17, 110, 120, 140, "mm"),
            new HeadCircumferenceEntity(18, 125, 135, 155, "mm"),
            new HeadCircumferenceEntity(19, 135, 150, 170, "mm"),
            new HeadCircumferenceEntity(20, 145, 160, 185, "mm"),
            new HeadCircumferenceEntity(21, 155, 175, 200, "mm"),
            new HeadCircumferenceEntity(22, 170, 190, 215, "mm"),
            new HeadCircumferenceEntity(23, 180, 205, 230, "mm"),
            new HeadCircumferenceEntity(24, 200, 220, 250, "mm"),
            new HeadCircumferenceEntity(25, 210, 235, 265, "mm"),
            new HeadCircumferenceEntity(26, 225, 250, 280, "mm"),
            new HeadCircumferenceEntity(27, 240, 265, 295, "mm"),
            new HeadCircumferenceEntity(28, 250, 280, 310, "mm"),
            new HeadCircumferenceEntity(29, 260, 290, 320, "mm"),
            new HeadCircumferenceEntity(30, 270, 300, 330, "mm"),
            new HeadCircumferenceEntity(31, 280, 310, 340, "mm"),
            new HeadCircumferenceEntity(32, 290, 320, 350, "mm"),
            new HeadCircumferenceEntity(33, 300, 330, 360, "mm"),
            new HeadCircumferenceEntity(34, 310, 340, 370, "mm"),
            new HeadCircumferenceEntity(35, 315, 350, 380, "mm"),
            new HeadCircumferenceEntity(36, 320, 355, 390, "mm"),
            new HeadCircumferenceEntity(37, 325, 360, 395, "mm"),
            new HeadCircumferenceEntity(38, 330, 365, 400, "mm"),
            new HeadCircumferenceEntity(39, 335, 370, 405, "mm"),
            new HeadCircumferenceEntity(40, 340, 375, 410, "mm")
    );

    public static List<HeadCircumferenceEntity> getAllHeadCircumferences() {
        return headCircumferenceList;
    }

    public static HeadCircumferenceEntity getHeadCircumferenceByWeek(int week) {
        return headCircumferenceList.stream()
                .filter(hc -> hc.getWeek() == week)
                .findFirst()
                .orElse(null);
    }
}