package com.example.thailand.User;

public class Order_model {
    String from, to,phone,weight,key_data;

    public Order_model() {
    }

    public Order_model(String from, String to, String phone, String weight, String key_data) {
        this.from = from;
        this.to = to;
        this.phone = phone;
        this.weight = weight;
        this.key_data = key_data;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getKey_data() {
        return key_data;
    }

    public void setKey_data(String key_data) {
        this.key_data = key_data;
    }
}
