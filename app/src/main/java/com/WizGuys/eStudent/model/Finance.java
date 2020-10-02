package com.WizGuys.eStudent.model;

import com.google.firebase.database.Exclude;

public class Finance {
    private String key;
    private String name;
    private String email;
    private String amount;
    private String balance;
    private String dueDate;
    private String date;
    private String description;

    public Finance() {
    }

    public Finance(String name,String email, String amount, String balance, String dueDate,
                   String date, String description) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.name = name;
        this.amount = amount;
        this.email = email;
        this.balance = balance;
        this.dueDate = dueDate;
        this.date = date;
        this.description = description;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Finance{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", amount='" + amount + '\'' +
                ", balance='" + balance + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
