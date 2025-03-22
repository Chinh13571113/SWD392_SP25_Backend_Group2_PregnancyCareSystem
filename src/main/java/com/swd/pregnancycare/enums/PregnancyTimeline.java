package com.swd.pregnancycare.enums;

import java.time.LocalDate;
import java.util.ArrayList;

public class PregnancyTimeline extends ArrayList<PregnancyPeriodEntity> {

    public PregnancyTimeline() {
        this.add(new PregnancyPeriodEntity
                (2,2,"Your baby is conceived",1,null));
        this.add(new PregnancyPeriodEntity(4, 4, "You'll have a positive pregnancy test", 1, null));
        this.add(new PregnancyPeriodEntity(6, 6, "Cells in your baby's future heart are flickering", 1, null));
        this.add(new PregnancyPeriodEntity(6, 8, "You may have your first prenatal visit", 1, null));
        this.add(new PregnancyPeriodEntity(10, 10, "You'll be offered NIPT (Noninvasive prenatal test)", 1, null));
        this.add(new PregnancyPeriodEntity(10, 13, "You can choose to have CVS (Chorionic villus sampling)", 1, null));
        this.add(new PregnancyPeriodEntity(11, 14, "You may have an NT scan and blood test", 1, null));
        this.add(new PregnancyPeriodEntity(12, 12, "You may hear your baby's heartbeat with a Doppler", 1, null));

        // Second Trimester
        this.add(new PregnancyPeriodEntity(15, 20, "You may have amniocentesis", 2, null));
        this.add(new PregnancyPeriodEntity(16, 18, "You may have a quad scan test", 2, null));
        this.add(new PregnancyPeriodEntity(16, 22, "You can feel your baby kick", 2, null));
        this.add(new PregnancyPeriodEntity(18, 22, "You'll have a mid-pregnancy ultrasound", 2, null));
        this.add(new PregnancyPeriodEntity(23, 23, "Your baby can hear you talk", 2, null));
        this.add(new PregnancyPeriodEntity(24, 28, "Glucose screening", 2, null));
        this.add(new PregnancyPeriodEntity(27, 27, "Your baby will open their eyes", 2, null));

        // Third Trimester
        this.add(new PregnancyPeriodEntity(28, 28, "You'll have prenatal visits every two weeks until 36 weeks, then weekly", 3, null));
        this.add(new PregnancyPeriodEntity(28, 28, "If you're Rh negative, you'll have a shot of Rh immune globulin (RhoGAM)", 3, null));
        this.add(new PregnancyPeriodEntity(34, 34, "Your baby has fingernails", 3, null));
        this.add(new PregnancyPeriodEntity(36, 37, "You'll be screened for Group B strep", 3, null));
        this.add(new PregnancyPeriodEntity(37, 37, "Your baby is early term", 3, null));
        this.add(new PregnancyPeriodEntity(39, 39, "Your baby is full term", 3, null));
        this.add(new PregnancyPeriodEntity(40, 40, "Your baby is due!", 3, null));
        this.add(new PregnancyPeriodEntity(41, 41, "If your labor doesn't start, you may be induced", 3, null));
    }

    public static LocalDate getCalculatedDueDate(LocalDate lastMenstrualPeriod, int cycleLength) {
        int standardCycle = 28;
        int pregnancyDays = 280;
        int adjustment = cycleLength - standardCycle;
        return lastMenstrualPeriod.plusDays(pregnancyDays + adjustment);
    }

    public void updateTimelineWithDates(LocalDate lastMenstrualPeriod, int cycleLength) {
        LocalDate dueDate = getCalculatedDueDate(lastMenstrualPeriod, cycleLength);
        for (PregnancyPeriodEntity period : this) {
            LocalDate eventDate = lastMenstrualPeriod.plusWeeks(period.getStartWeek()).plusDays(cycleLength-28);
            period.setSuggestDate(eventDate);
        }
    }
    public static LocalDate getDueDateFromUltrasound(LocalDate ultrasoundDate, int gestationalWeeks, int gestationalDays) {
        int totalGestationalDays = (gestationalWeeks * 7) + gestationalDays;
        return ultrasoundDate.minusDays(totalGestationalDays).plusDays(280);
    }
    public void updateTimelineWithDueDate(LocalDate dueDate) {
        for (PregnancyPeriodEntity period : this) {
            LocalDate eventDate = dueDate.minusWeeks(40 - period.getStartWeek());
            period.setSuggestDate(eventDate);
        }
    }
}
