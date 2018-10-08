package com.example.arnavdesai.burrp;

public class Owner {
    public String name,messName,address,email,phone,password,type,locality;

    public Owner() {
    }

    public Owner(String name, String messName, String address, String email, String phone, String password,String locality) {
        this.name = name;
        this.messName = messName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.locality=locality;
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
