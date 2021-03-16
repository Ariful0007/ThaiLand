package com.example.thailand.Admin;

public class Notification {
    String driver_formate, key,from,to,weight,phone;

    public Notification() {
    }

    public Notification(String driver_formate,
                        String key, String from, String to,
                        String weight, String phone) {
        this.driver_formate = driver_formate;
        this.key = key;
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.phone = phone;
    }

    public String getDriver_formate() {
        return driver_formate;
    }

    public void setDriver_formate(String driver_formate) {
        this.driver_formate = driver_formate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
