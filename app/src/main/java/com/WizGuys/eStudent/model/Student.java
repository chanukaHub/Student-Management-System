package com.WizGuys.eStudent.model;
import com.google.firebase.database.Exclude;

public class Student {
    private String key;
    private String name;
    private String index;
    private String address;
    private String contact;
    private String email;
    private String imageURL;

    public Student() {
    }

    public Student(String name, String index, String address, String contact, String email, String imageURL) {
        this.name = name;
        this.index = index;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.imageURL = imageURL;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Student{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", index='" + index + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
