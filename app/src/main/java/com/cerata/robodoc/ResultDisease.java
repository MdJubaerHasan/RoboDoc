package com.cerata.robodoc;

public class ResultDisease {
    private String disease_name;
    private String symptoms;
    private int percentage_matched;

    public ResultDisease(String disease_name, String symptoms, int percentage_matched) {
        this.disease_name = disease_name;
        this.symptoms = symptoms;
        this.percentage_matched = percentage_matched;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public int getPercentage_matched() {
        return percentage_matched;
    }

    public void setPercentage_matched(int percentage_matched) {
        this.percentage_matched = percentage_matched;
    }
}
