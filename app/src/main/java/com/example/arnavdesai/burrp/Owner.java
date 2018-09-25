package com.example.arnavdesai.burrp;

/**
 * Created by Arnav Desai on 9/17/2018.
 */

public class Owner {
    public String name,messName,address,email,phone,password,type;

    public Owner() {
    }


    public Owner(String name, String messName, String address, String email, String phone, String password) {
        this.name = name;
        this.messName = messName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        type="Owner";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
