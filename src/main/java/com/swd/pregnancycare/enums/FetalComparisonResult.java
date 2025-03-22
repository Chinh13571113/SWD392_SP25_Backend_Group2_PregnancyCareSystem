package com.swd.pregnancycare.enums;

public class FetalComparisonResult {private int week;
    private String headCircumferenceResult;
    private String fetalLengthResult;
    private String fetalWeightResult;

    public FetalComparisonResult(int week, String headCircumferenceResult, String fetalLengthResult, String fetalWeightResult) {
        this.week = week;
        this.headCircumferenceResult = headCircumferenceResult;
        this.fetalLengthResult = fetalLengthResult;
        this.fetalWeightResult = fetalWeightResult;
    }

    public int getWeek() { return week; }
    public String getHeadCircumferenceResult() { return headCircumferenceResult; }
    public String getFetalLengthResult() { return fetalLengthResult; }
    public String getFetalWeightResult() { return fetalWeightResult; }
}
