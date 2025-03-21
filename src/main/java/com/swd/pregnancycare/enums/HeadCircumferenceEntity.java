package com.swd.pregnancycare.enums;


public class HeadCircumferenceEntity {
    private int week;
    private double lowerBound;
    private double average;
    private double upperBound;
    private String unit;

    // Constructor
    public HeadCircumferenceEntity(int week, double lowerBound, double average, double upperBound, String unit) {
        this.week = week;
        this.lowerBound = lowerBound;
        this.average = average;
        this.upperBound = upperBound;
        this.unit = unit;
    }

    // Getters (Quan trọng để tránh lỗi)
    public int getWeek() { return week; }
    public double getLowerBound() { return lowerBound; }
    public double getAverage() { return average; }
    public double getUpperBound() { return upperBound; }
    public String getUnit() { return unit; }
}

