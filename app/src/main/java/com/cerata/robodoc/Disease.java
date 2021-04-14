package com.cerata.robodoc;

public class Disease implements Comparable{
    private String name;
    private String symptom;
    private int numberOfSymptoms;
    private short  percentage;

    public Disease(String name, String symptom, int numberOfSymptoms) {
        this.name = name;
        this.symptom = symptom;
        this.numberOfSymptoms = numberOfSymptoms;
    }
    public Disease(String name, String symptom, short percentage) {
        this.name = name;
        this.symptom = symptom;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public int getNumberOfSymptoms() {
        return numberOfSymptoms;
    }

    public void setNumberOfSymptoms(int numberOfSymptoms) {
        this.numberOfSymptoms = numberOfSymptoms;
    }

    public short getPercentage() {
        return percentage;
    }

    public void setPercentage(short percentage) {
        this.percentage = percentage;
    }

    @Override
    public int compareTo(Object o) {
        short comparePercentage = ((Disease)o).getPercentage();
        return (int)(comparePercentage-this.percentage);
    }
}
