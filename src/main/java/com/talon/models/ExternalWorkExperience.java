package com.talon.models;

public class ExternalWorkExperience extends BaseWorkExperience {
    private String reasonForLeaving;

    public ExternalWorkExperience(String companyName, String position, String startDate, String endDate, String reasonForLeaving) {
        super(companyName, position, startDate, endDate);
        this.reasonForLeaving = reasonForLeaving;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }
}