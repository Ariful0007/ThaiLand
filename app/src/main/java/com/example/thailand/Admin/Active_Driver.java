package com.example.thailand.Admin;

public class Active_Driver {
    String driver_id,username,phonenumber,vehicle_Weight,vehicle_Length;

    public Active_Driver() {
    }

    public Active_Driver(String driver_id, String username,
                         String phonenumber, String vehicle_Weight, String vehicle_Length) {
        this.driver_id = driver_id;
        this.username = username;
        this.phonenumber = phonenumber;
        this.vehicle_Weight = vehicle_Weight;
        this.vehicle_Length = vehicle_Length;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getVehicle_Weight() {
        return vehicle_Weight;
    }

    public void setVehicle_Weight(String vehicle_Weight) {
        this.vehicle_Weight = vehicle_Weight;
    }

    public String getVehicle_Length() {
        return vehicle_Length;
    }

    public void setVehicle_Length(String vehicle_Length) {
        this.vehicle_Length = vehicle_Length;
    }
}
