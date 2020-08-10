package com.WizGuys.eStudent;

public class User {
    String name,password,userName;
    int age;

    public User(String name, int age, String password, String userName) {
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.age = age;
    }

    public User(String password, String userName) {
        this.password = password;
        this.userName = userName;
        this.age = -1;
        this.name = "";
    }

}
