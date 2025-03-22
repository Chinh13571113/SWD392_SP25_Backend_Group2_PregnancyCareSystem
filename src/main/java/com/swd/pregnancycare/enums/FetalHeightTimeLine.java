package com.swd.pregnancycare.enums;

import java.util.List;

public  class FetalHeightTimeLine {
        private static final List<FetalHeightEntity> fetalHeightList = List.of(
                new FetalHeightEntity(12, 50, 55, 60, "mm"),
                new FetalHeightEntity(13, 60, 70, 80, "mm"),
                new FetalHeightEntity(14, 80, 90, 100, "mm"),
                new FetalHeightEntity(15, 90, 100, 115, "mm"),
                new FetalHeightEntity(16, 100, 120, 135, "mm"),
                new FetalHeightEntity(17, 110, 140, 150, "mm"),
                new FetalHeightEntity(18, 120, 160, 170, "mm"),
                new FetalHeightEntity(19, 130, 180, 190, "mm"),
                new FetalHeightEntity(20, 140, 200, 210, "mm"),
                new FetalHeightEntity(21, 160, 220, 230, "mm"),
                new FetalHeightEntity(22, 180, 240, 250, "mm"),
                new FetalHeightEntity(23, 200, 260, 270, "mm"),
                new FetalHeightEntity(24, 220, 280, 290, "mm"),
                new FetalHeightEntity(25, 240, 300, 310, "mm"),
                new FetalHeightEntity(26, 260, 320, 330, "mm"),
                new FetalHeightEntity(27, 280, 340, 350, "mm"),
                new FetalHeightEntity(28, 300, 360, 370, "mm"),
                new FetalHeightEntity(29, 320, 380, 390, "mm"),
                new FetalHeightEntity(30, 340, 400, 410, "mm"),
                new FetalHeightEntity(31, 360, 420, 430, "mm"),
                new FetalHeightEntity(32, 380, 440, 450, "mm"),
                new FetalHeightEntity(33, 400, 460, 470, "mm"),
                new FetalHeightEntity(34, 420, 480, 490, "mm"),
                new FetalHeightEntity(35, 440, 500, 510, "mm"),
                new FetalHeightEntity(36, 460, 520, 530, "mm"),
                new FetalHeightEntity(37, 480, 540, 550, "mm"),
                new FetalHeightEntity(38, 500, 560, 570, "mm"),
                new FetalHeightEntity(39, 520, 580, 590, "mm"),
                new FetalHeightEntity(40, 540, 600, 610, "mm")
        );

        public static List<FetalHeightEntity> getAllFetalHeights() {
            return fetalHeightList;
        }

        public static FetalHeightEntity getFetalHeightByWeek(int week) {
            return fetalHeightList.stream()
                    .filter(fh -> fh.getWeek() == week)
                    .findFirst()
                    .orElse(null);
        }
    }
