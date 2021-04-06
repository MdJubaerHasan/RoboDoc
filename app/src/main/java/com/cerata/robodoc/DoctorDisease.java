package com.cerata.robodoc;

public class DoctorDisease {
    private String doctor_name;
    private String disease_name;

    public DoctorDisease(String doctor_name, String disease_name) {
        this.doctor_name = doctor_name;
        this.disease_name = disease_name;
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
}
