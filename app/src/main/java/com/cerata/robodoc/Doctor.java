package com.cerata.robodoc;

public class Doctor implements Comparable{
    private String name;
    private String chamber;
    private String email;
    private String contact;
    private String latitude;
    private String longitude;
    private double distance;
    private String chamberMap;

    public Doctor(String name, String chamber, String email, String contact, String chamberMap, String latitude, String longitude) {
        this.name = name;
        this.chamber = chamber;
        this.email = email;
        this.contact = contact;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chamberMap = chamberMap;
    }
    public Doctor(String name, double distance){
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getChamberMap() {
        return chamberMap;
    }

    public void setChamberMap(String chamberMap) {
        this.chamberMap = chamberMap;
    }

    @Override
    public int compareTo(Object o) {
        double compareDistance = ((Doctor)o).getDistance();
        // Ascending order
        return (int) (this.distance-compareDistance);
    }

}
