package com.example.arnavdesai.burrp;

import static android.R.attr.type;

public class Student {
    public String name,college,phone,email,password,type;

    public Student()
    {

    }


    public Student(String name, String college, String phone, String email, String password) {
        this.name = name;
        this.college = college;
        this.phone = phone;
        this.email = email;
        this.password = password;
        type="Student";
    }
}
