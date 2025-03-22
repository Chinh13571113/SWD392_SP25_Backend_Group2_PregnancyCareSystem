package com.swd.pregnancycare.enums;

public class FetalWeightEntity {
    private int week;
    private double lowerBound;
    private double average;
    private double upperBound;
    private String unit;

    // Constructor
    public FetalWeightEntity(int week, double lowerBound, double average, double upperBound, String unit) {
        this.week = week;
        this.lowerBound = lowerBound;
        this.average = average;
        this.upperBound = upperBound;
        this.unit = unit;
    }

    // Getters
    public int getWeek() { return week; }
    public double getLowerBound() { return lowerBound; }
    public double getAverage() { return average; }
    public double getUpperBound() { return upperBound; }
    public String getUnit() { return unit; }
}
