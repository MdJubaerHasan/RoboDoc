package com.cerata.robodoc;

public class DoctorDisease {
    private String doctor_name;
    private String disease_name;
    private double doctor_distance;

    public DoctorDisease(String doctor_name, String disease_name) {
        this.doctor_name = doctor_name;
        this.disease_name = disease_name;
    }
    public DoctorDisease(String doctor_name, String disease_name, double doctor_distance){
        this.doctor_name = doctor_name;
        this.disease_name = disease_name;
        this.doctor_distance = doctor_distance;
    }



    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public double getDoctor_distance() {
        return doctor_distance;
    }

    public void setDoctor_distance(double doctor_distance) {
        this.doctor_distance = doctor_distance;
    }
}
