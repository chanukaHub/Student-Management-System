//package com.WizGuys.eStudent.model;
//
//import com.google.firebase.database.Exclude;
//
//public class Teacher {
//    private String key;
//    private String name;
//    private String address;
//    private String contact;
//    private String description;
//    private String email;
//    private String imageURL;
//    private String qualification;
//    private String salary;
//    public Teacher() {
//        //empty constructor needed
//    }
//
//    public Teacher(String name, String address,
//                   String contact, String description, String email,
//                   String imageURL, String qualification,
//                   String salary) {
//        if (name.trim().equals("")) {
//            name = "No Name";
//        }
//        this.name = name;
//        this.address = address;
//        this.contact = contact;
//        this.description = description;
//        this.email = email;
//        this.imageURL = imageURL;
//        this.qualification = qualification;
//        this.salary = salary;
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getContact() {
//        return contact;
//    }
//
//    public void setContact(String contact) {
//        this.contact = contact;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }
//
//    public String getQualification() {
//        return qualification;
//    }
//
//    public void setQualification(String qualification) {
//        this.qualification = qualification;
//    }
//
//    public String getSalary() {
//        return salary;
//    }
//
//    public void setSalary(String salary) {
//        this.salary = salary;
//    }
//
//    @Exclude
//    public String getKey() {
//        return key;
//    }
//    @Exclude
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    @Override
//    public String toString() {
//        return "Teacher{" +
//                "key='" + key + '\'' +
//                ", name='" + name + '\'' +
//                ", address='" + address + '\'' +
//                ", contact='" + contact + '\'' +
//                ", description='" + description + '\'' +
//                ", email='" + email + '\'' +
//                ", imageURL='" + imageURL + '\'' +
//                ", qualification='" + qualification + '\'' +
//                ", salary='" + salary + '\'' +
//                '}';
//    }
//}
