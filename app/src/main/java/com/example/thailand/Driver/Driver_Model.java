package com.example.thailand.Driver;

public class Driver_Model {
    String username, username2,
            phoneNumber, Vehicle_Weight, Vehicle_Length,
            Vehicle_Width, Email_Address, Password, Driver_id;

    public Driver_Model() {
    }

    public Driver_Model(String username, String username2, String phoneNumber, String vehicle_Weight,
                        String vehicle_Length, String vehicle_Width,
                        String email_Address, String password, String driver_id) {
        this.username = username;
        this.username2 = username2;
        this.phoneNumber = phoneNumber;
        Vehicle_Weight = vehicle_Weight;
        Vehicle_Length = vehicle_Length;
        Vehicle_Width = vehicle_Width;
        Email_Address = email_Address;
        Password = password;
        Driver_id = driver_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVehicle_Weight() {
        return Vehicle_Weight;
    }

    public void setVehicle_Weight(String vehicle_Weight) {
        Vehicle_Weight = vehicle_Weight;
    }

    public String getVehicle_Length() {
        return Vehicle_Length;
    }

    public void setVehicle_Length(String vehicle_Length) {
        Vehicle_Length = vehicle_Length;
    }

    public String getVehicle_Width() {
        return Vehicle_Width;
    }

    public void setVehicle_Width(String vehicle_Width) {
        Vehicle_Width = vehicle_Width;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDriver_id() {
        return Driver_id;
    }

    public void setDriver_id(String driver_id) {
        Driver_id = driver_id;
    }
}