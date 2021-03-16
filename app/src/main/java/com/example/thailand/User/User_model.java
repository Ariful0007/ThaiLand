package com.example.thailand.User;

public class User_model {
    String username,address,phoneNumber,emailAddress,password_username,customer_id;

    public User_model() {
    }

    public User_model(String username, String address, String phoneNumber,
                      String emailAddress, String password_username, String customer_id) {
        this.username = username;

        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password_username = password_username;
        this.customer_id = customer_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword_username() {
        return password_username;
    }

    public void setPassword_username(String password_username) {
        this.password_username = password_username;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
